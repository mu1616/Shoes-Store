package com.example.store.controller.admin.product;

import java.util.List;

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

import com.example.store.model.brand.dao.BrandDAO;
import com.example.store.model.category.dao.CategoryDAO;
import com.example.store.model.pagination.dto.PageDTO;
import com.example.store.model.product.dao.ProductDAO;
import com.example.store.model.product.dto.ProductDTO;
import com.example.store.model.product.dto.ProductEntity;
import com.example.store.model.search.dto.SearchDTO;
import com.example.store.service.admin.product.ManageProductService;
import com.example.store.util.Paging;

@Controller
public class ManageProductController {
	@Autowired
	ManageProductService productService;
	@Value("${file.upload.directory}")
	String filePath;
	@Value("${smarteditor.upload.directory}")
	String editorPath;
	@Autowired
	ProductDAO productDao;
	@Autowired
	Paging pageService;
	@Autowired
	CategoryDAO categoryDao;
	@Autowired
	BrandDAO brandDao;
	
	//상품등록 페이지
	@RequestMapping(value = "/admin/product/register", method = RequestMethod.GET)
	public String registerPage(Model model) {
		model.addAttribute("categoryList",categoryDao.selectAll());
		model.addAttribute("brandList",brandDao.selectAll());
		return "admin/admin";
	}

	//상품등록시 처리
	@RequestMapping(value = "/admin/product/register", method = RequestMethod.POST)
	public String registerProduct(@ModelAttribute ProductEntity productEntity, @RequestPart("profile") MultipartFile files,
			@RequestParam(value = "mainDisplay", required = false) int[] mainDisplay, 
			int size[], int count[]) {
		productService.register(productEntity, files, mainDisplay, size, count);
		return "redirect:/admin/product/list/1";
	}
	
	//상품관리 상품리스트 페이지
	@RequestMapping("/admin/product/list/{currentPage}")
	public String productList(@PathVariable(value = "currentPage", required = false) int currentPage, 
			@ModelAttribute SearchDTO searchDto, Model model, String searchOption, 
			@RequestParam(value="search", required=false)String searchWord) {			
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
		model.addAttribute("categoryList",categoryDao.selectAll());
		model.addAttribute("brandList",brandDao.selectAll());
		return "/admin/admin_productlist";
	}
	
	//상품삭제 
	@ResponseBody
	@RequestMapping("/admin/product/delete")
	public void productDelete(@RequestParam("product_idx") String[] product_idx) {
		//for(String a : product_idx) System.out.println(a);
		productDao.deleteProduct(product_idx);
	}
	
	//상품수정 페이지
	@RequestMapping(value = "/admin/product/modify", method = RequestMethod.GET)
	public String modifyPage(int product_idx, Model model) {
		ProductEntity productEntity = productDao.selectOne(product_idx);
		model.addAttribute("productEntity",productEntity);
		model.addAttribute("categoryList",categoryDao.selectAll());
		model.addAttribute("brandList",brandDao.selectAll());
		return "admin/admin_productmodify";
	}
	
	//상품 수정요청시 처리
	@RequestMapping(value = "/admin/product/modify", method = RequestMethod.POST)
	public String modify(@ModelAttribute ProductEntity productEntity, int product_idx,
			@RequestPart(value="profile", required=false) MultipartFile files) {
		productService.modifyProduct(productEntity, files, product_idx);
		return "admin/admin_modifyComplete";
	}
	
}
