package com.example.project_01.controller.admin.product;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.List;
import java.util.Optional;

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
import com.example.project_01.model.product.dto.ProductDTO;
import com.example.project_01.model.product.dto.ProductEntity;
import com.example.project_01.model.stock.dao.StockDAO;
import com.example.project_01.model.stock.dto.StockDTO;
import com.example.project_01.service.admin.product.ManageProductService;

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

	@RequestMapping(value = "/admin/product/register", method = RequestMethod.GET)
	public String registerPage() {
		return "admin";
	}

	@RequestMapping(value = "/admin/product/register", method = RequestMethod.POST)
	public String registerProduct(@ModelAttribute ProductEntity productEntity, @RequestPart("profile") MultipartFile files,
			@RequestParam(value = "mainDisplay", required = false) int[] mainDisplay, 
			int size[], int count[]) {
		//for(int num : size) System.out.println(num);
		//for(int num : count) System.out.println(num);
		productService.register(productEntity, files, mainDisplay, size, count);
		return "admin";
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

	@RequestMapping("/admin/product/list/{idx}")
	public String productList(@PathVariable(value = "idx", required = false) Optional<Integer> idx, Model model) {
		int currentPage = 1;
		if (idx.isPresent())
			currentPage = idx.get();
		PageDTO pageDto = productService.calPage(currentPage);
		System.out.println(pageDto);
		model.addAttribute("pageDto", pageDto);
		List<ProductDTO> productList = productService.selectProduct(currentPage);
		model.addAttribute("productList", productList);
		return "admin_productlist";
	}
	@RequestMapping("/admin/product/stock")
	public String showStock(Model model, int product_idx) {
		List<StockDTO> stockList = stockDao.selectByProduct(product_idx);
		model.addAttribute("stockList", stockList);
		model.addAttribute("product_idx",product_idx);
		return "popup/stock";
	}
	
	@ResponseBody
	@RequestMapping("/admin/product/stock/add")
	public void addStock(int product_idx, int size, int count) {
		System.out.printf("%d %d %d", product_idx,size,count);
		stockDao.insertStock(product_idx, size, count);
		
	}
	
	@ResponseBody
	@RequestMapping("/admin/product/stock/modify")
	public void modifyStock(int product_idx, int size, int count) {
		System.out.printf("%d %d %d", product_idx,size,count);
		stockDao.updateStock(product_idx, size, count);
		
	}
	
	@ResponseBody
	@RequestMapping("/admin/product/stock/delete")
	public void deleteStock(int product_idx, int size) {
		stockDao.deleteStock(product_idx, size);
		
	}
}
