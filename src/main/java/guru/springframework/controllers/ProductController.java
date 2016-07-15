package guru.springframework.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import guru.springframework.domain.Product;
import guru.springframework.services.interfaces.ProductService;

@Controller
public class ProductController {

	private ProductService productService;
	
	@Autowired
	public void setProductService(ProductService productService) {
		this.productService = productService;
	}
	
	@RequestMapping("/products")
	public String listProducts(Model model) {
		model.addAttribute("products",productService.listAll());
		return "product/products";
	}
	
	@RequestMapping("/product/{id}")
	public String getProduct(@PathVariable Integer id, Model model) {
		model.addAttribute("product",productService.findById(id));
		return "product/product";
	}
	
	@RequestMapping("/product/edit/{id}")
	public String edit(@PathVariable Integer id, Model model) {
		model.addAttribute("product", productService.findById(id));
		return "product/productForm";
	}
	
	@RequestMapping("/product/new")
	public String newProduct(Model model) {
		model.addAttribute("product",new Product());
		return "product/productForm";
	}
	
	@RequestMapping(value="/product", method = RequestMethod.POST)
	public String saveOrUpdateProduct(Product product) {
		Product savedProduct = productService.saveOrUpdate(product);
		//remember that redirect has to point to a controller url, not the the template directory
		return "redirect:/product/"+savedProduct.getId();
	}
	
	@RequestMapping("/product/delete/{id}")
	public String delete(@PathVariable Integer id) {
		productService.delete(id);;
		return  "redirect:/products";
	}
}
