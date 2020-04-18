package com.example.project_01.controller.admin.product;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.example.project_01.model.pagination.dto.PageDTO;
import com.example.project_01.model.product.dao.ProductDAO;
import com.example.project_01.model.product.dto.ProductDTO;
import com.example.project_01.model.product.dto.ProductEntity;
import com.example.project_01.model.search.dto.SearchDTO;
import com.example.project_01.model.stock.dao.StockDAO;
import com.example.project_01.model.stock.dto.StockDTO;
import com.example.project_01.service.admin.product.ManageProductService;
import com.example.project_01.service.pagination.PageService;

@Controller
public class ManageProductController {
	@Autowired
	ManageProductService productService;
	@Value("${file.upload.directory}")
	String filePath;
	@Value("${smarteditor.upload.directory}")
	String editorPath;
	@Autowired
	StockDAO stockDao;
	@Autowired
	ProductDAO productDao;
	@Autowired
	PageService pageService;
	
	@RequestMapping(value = "/admin/product/register", method = RequestMethod.GET)
	public String registerPage() {
		return "admin/admin";
	}

	@RequestMapping(value = "/admin/product/register", method = RequestMethod.POST)
	public String registerProduct(@ModelAttribute ProductEntity productEntity, @RequestPart("profile") MultipartFile files,
			@RequestParam(value = "mainDisplay", required = false) int[] mainDisplay, 
			int size[], int count[]) {
		productService.register(productEntity, files, mainDisplay, size, count);
		return "redirect:/admin/product/list/1";
	}

	@ResponseBody
	@RequestMapping("/admin/product/fileupload")
	public void upload(HttpServletRequest request, HttpServletResponse response) {
		try { // 파일정보
			String sFileInfo = "";
			// 파일명을 받는다 - 일반 원본파일명
			String filename = request.getHeader("file-name");
			// 파일 확장자
			String filename_ext = filename.substring(filename.lastIndexOf(".") + 1);
			// 확장자를소문자로 변경
			filename_ext = filename_ext.toLowerCase();
			// 이미지 검증 배열변수
			String[] allow_file = { "jpg", "png", "bmp", "gif" };
			// 돌리면서 확장자가 이미지인지
			int cnt = 0;
			for (int i = 0; i < allow_file.length; i++) {
				if (filename_ext.equals(allow_file[i])) {
					cnt++;
				}
			}
			// 이미지가 아님
			if (cnt == 0) {
				PrintWriter print = response.getWriter();
				print.print("NOTALLOW_" + filename);
				print.flush();
				print.close();
			} else {
				// 이미지이므로 신규 파일로 디렉토리 설정 및 업로드
				File file = new File(editorPath);
				if (!file.exists()) {
					file.mkdirs();
				}
				String realFileNm = "";
				realFileNm = productService.makeFileName(filename);
				String rlFileNm = editorPath + realFileNm;
				///////////////// 서버에 파일쓰기 /////////////////
				InputStream is = request.getInputStream();
				OutputStream os = new FileOutputStream(rlFileNm);
				int numRead;
				byte b[] = new byte[Integer.parseInt(request.getHeader("file-size"))];
				while ((numRead = is.read(b, 0, b.length)) != -1) {
					os.write(b, 0, numRead);
				}
				if (is != null) {
					is.close();
				}
				os.flush();
				os.close();
				///////////////// 서버에 파일쓰기
				///////////////// // 정보 출력
				sFileInfo += "&bNewLine=true";
				// img 태그의 title 속성을 원본파일명으로 적용시켜주기 위함
				sFileInfo += "&sFileName=" + filename;
				sFileInfo += "&sFileURL=" + editorPath + realFileNm;
				PrintWriter print = response.getWriter();
				print.print(sFileInfo);
				print.flush();
				print.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	@RequestMapping("/admin/product/list/{currentPage}")
	public String productList(@PathVariable(value = "currentPage", required = false) int currentPage, 
			@ModelAttribute SearchDTO searchDto, Model model, String searchOption, 
			@RequestParam("search")String searchWord) {			
		if(currentPage <=0) 
			return "redirect:/admin/product/list/1";
		if(searchOption !=null) {
			if(searchOption.equals("상품명")) {
				searchDto.setProduct_name("%"+searchWord+"%");
			}
			if(searchOption.equals("상품번호")) 
				if(searchWord.equals("")) {
					searchDto.setProduct_idx("%");
				} else {
					searchDto.setProduct_idx(searchWord);
				}
		}		
		int totalRecord = productDao.countProduct(searchDto);
		PageDTO pageDto = pageService.calPage(currentPage, 10, totalRecord, 10);
		if(currentPage > pageDto.getTotalPage() && pageDto.getCountRecord()!=0) 
			return "redirect:/admin/product/list/"+pageDto.getTotalPage();		
		model.addAttribute("pageDto", pageDto);
		List<ProductDTO> productList = productService.selectProduct(currentPage, 10, searchDto);
		model.addAttribute("productList", productList);
		searchDto.setProduct_name(searchWord);
		searchDto.setProduct_idx(searchWord);
		model.addAttribute("searchDto",searchDto);
		model.addAttribute("searchOption",searchOption);
		return "/admin/admin_productlist";
	}
	@RequestMapping("/admin/product/stock")
	public String showStock(Model model, int product_idx) {
		List<StockDTO> stockList = stockDao.selectByProduct(product_idx);
		model.addAttribute("stockList", stockList);
		model.addAttribute("product_idx",product_idx);
		ProductEntity productEntity = productDao.selectOne(product_idx);
		model.addAttribute("productEntity",productEntity);
		return "popup/stock";
	}
	
	@ResponseBody
	@RequestMapping("/admin/product/stock/add")
	public void addStock(int product_idx, int size, int count) {
		stockDao.insertStock(product_idx, size, count);		
	}
	
	@ResponseBody
	@RequestMapping("/admin/product/stock/modify")
	public void modifyStock(int product_idx, int size, int count) {
		stockDao.updateStock(product_idx, size, count);
		
	}
	
	@ResponseBody
	@RequestMapping("/admin/product/stock/delete")
	public void deleteStock(int product_idx, int size) {
		stockDao.deleteStock(product_idx, size);
		
	}
	
	@ResponseBody
	@RequestMapping("/admin/product/delete")
	public void productDelete(@RequestParam("product_idx") String[] product_idx) {
		//for(String a : product_idx) System.out.println(a);
		productDao.deleteProduct(product_idx);
	}
	
	@RequestMapping(value = "/admin/product/modify", method = RequestMethod.GET)
	public String modifyPage(int product_idx, Model model) {
		ProductEntity productEntity = productDao.selectOne(product_idx);
		model.addAttribute("productEntity",productEntity);
		return "admin/admin_productmodify";
	}
	
	@RequestMapping(value = "/admin/product/modify", method = RequestMethod.POST)
	public String modify(@ModelAttribute ProductEntity productEntity, int product_idx,
			@RequestPart(value="profile", required=false) MultipartFile files) {
		
		if(files != null) {
			productService.fileUpload(productEntity, files);
		} else {
			productEntity.setProduct_image(null);
		}
		productDao.updateProduct(productEntity, product_idx);
		return "admin_modifyComplete";
	}
	
	@RequestMapping("/admin/product/mainDisplay")
	public String mainDisplay(@RequestParam(value="display_md", defaultValue="1")int display_md,
			Model model) {
		List<ProductDTO> productList = productDao.selectProductByDisplay(display_md);
		model.addAttribute("productList",productList);
		model.addAttribute("category",display_md);
		return "admin/admin_mainDisplay";
	}
	
	@RequestMapping(value="/admin/product/mainDisplay/add", method=RequestMethod.GET)
	public String mainDisplayAdd(int display_md, Model model) {
		model.addAttribute("display_md",display_md);
		return "popup/mainDisplayAdd";
	}
	
	@RequestMapping(value="/admin/product/mainDisplay/add/search", method=RequestMethod.POST)
	public String mainDisplayAdd_Search(String searchOption, String searchWord, Model model) {
		SearchDTO searchDto = new SearchDTO();
		if(searchOption.equals("상품명"))
			searchDto.setProduct_name("%"+searchWord+"%");
		if(searchOption.equals("상품번호"))
			searchDto.setProduct_idx(searchWord);
		List<ProductDTO> productList = productDao.selectProduct(0, 20, searchDto);
		model.addAttribute("productList",productList);
		return "popup/searchList";
	}
	
	@ResponseBody
	@RequestMapping("/admin/product/mainDisplay/add/save")
	public void mainDisplayAdd_save(int []product_idx, int display_md) {
		for(int idx : product_idx)
			productDao.addProductMainDisplay(idx, display_md);
		
	}
	
	@ResponseBody
	@RequestMapping("/admin/product/mainDisplay/save")
	public void mainDisplay_save(int []product_idx, int display_md) {
		productService.saveDisplay(display_md, product_idx);
	}
}
