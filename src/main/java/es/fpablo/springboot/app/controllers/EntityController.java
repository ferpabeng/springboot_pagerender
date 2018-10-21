package es.fpablo.springboot.app.controllers;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import es.fpablo.springboot.app.models.entity.EntityClazz;
import es.fpablo.springboot.app.models.service.IEntityService;
import es.fpablo.springboot.app.util.paginator.PageRender;

/**
 * 
 * @author ferpabeng
 *
 */
@Controller
@SessionAttributes("entity")
public class EntityController {

	private static final String LITERAL_PAGE = "page";
	private static final String LITERAL_SUCCESS = "success";
	private static final String LITERAL_ERROR = "error";
	
	private static final String TEMPLATE_FORM = "form";
	
	private static final String URL_DELETE = "/delete";
	private static final String URL_FORM = "/form";
	private static final String URL_LIST = "/list";
	
	private static final String REDIRECT_LIST = "redirect:/list";
	
	private static final String TITLE_PAGE = "titulo";
	
	@Autowired
	private IEntityService entityService;

	@RequestMapping(value = URL_LIST, method = RequestMethod.GET)
	public String list(@RequestParam(name = LITERAL_PAGE, defaultValue = "0") int page, Model model) {

		Pageable pageRequest = new PageRequest(page, 4);
		Page<EntityClazz> entityClazzPages = entityService.findAll(pageRequest);
		PageRender<EntityClazz> pageRender = new PageRender<>(URL_LIST, entityClazzPages);

		model.addAttribute(TITLE_PAGE, "EntityClazz List");
		model.addAttribute("entities", entityClazzPages);
		model.addAttribute(LITERAL_PAGE, pageRender);

		return "list";
	}

	@RequestMapping(value = URL_FORM)
	public String create(Map<String, Object> model) {

		EntityClazz entityClazz = new EntityClazz();
		model.put("entity", entityClazz);
		model.put(TITLE_PAGE, "Create");
		
		return TEMPLATE_FORM;
	}

	@RequestMapping(value = URL_FORM+"/{id}")
	public String edit(@PathVariable(value = "id") Long id, Map<String, Object> model, RedirectAttributes flash) {

		EntityClazz entityClazz = null;

		if (id > 0) {
			entityClazz = entityService.findOne(id);
			if (entityClazz == null) {
				flash.addFlashAttribute(LITERAL_ERROR, "The ID of the entity does not exist in the BBDD!");
				return REDIRECT_LIST;
			}
		} else {
			flash.addFlashAttribute(LITERAL_ERROR, "Entity ID can't be null!");
			return REDIRECT_LIST;
		}
		model.put("entity", entityClazz);
		model.put(TITLE_PAGE, "Edit");
		return TEMPLATE_FORM;
	}

	@RequestMapping(value = URL_FORM, method = RequestMethod.POST)
	public String save(@Valid EntityClazz entityClazz, BindingResult result, Model model, RedirectAttributes flash,
			SessionStatus status) {

		if (result.hasErrors()) {
			model.addAttribute(TITLE_PAGE, "Entity For,");
			
			return TEMPLATE_FORM;
		}
		
		String mensajeFlash = (entityClazz.getId() != null) ? "Entity successfully edited!"
				: "Entity created successfully!";

		entityService.save(entityClazz);
		status.setComplete();
		flash.addFlashAttribute(LITERAL_SUCCESS, mensajeFlash);
		
		return "redirect:list";
	}

	@RequestMapping(value = URL_DELETE+"/{id}")
	public String delete(@PathVariable(value = "id") Long id, RedirectAttributes flash) {

		if (id > 0) {
			entityService.delete(id);
			flash.addFlashAttribute(LITERAL_SUCCESS, "Entity successfully deleted!");
		}
		return REDIRECT_LIST;
	}
}
