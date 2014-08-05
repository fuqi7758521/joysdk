package com.joysdk.acs.controller.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.joysdk.acs.Constant;
import com.joysdk.acs.criteria.ResourceCriteria;
import com.joysdk.acs.exception.AcsException;
import com.joysdk.acs.service.ResourceService;
import com.joysdk.common.acs.model.Resource;
import com.joysdk.common.util.RequestUtil;

@Controller
@RequestMapping("/acsAdmin/resource")
public class ResourceController {

	@Autowired
	private ResourceService resourceService;
	
	@RequestMapping("/addUrl")
    public ModelAndView addUrl(HttpServletRequest request,HttpServletResponse response) throws AcsException{
        return new ModelAndView("resource/editUrl");
    }
	
	@RequestMapping("/saveUrl")
    public ModelAndView saveRole(Resource resource, HttpServletRequest request,HttpServletResponse response) throws AcsException{
        ModelAndView mav=new ModelAndView("resource/editUrl");
        
        String url=RequestUtil.getString(request, "url");
        
        if(StringUtils.isEmpty(url)){
            return mav.addObject("errorMsg", "请填写url地址"); 
        }
        if(resource.getId() == null){
        	resource.setType(Constant.RESOURCE_TYPE_URL);
        	resourceService.add(resource);
        }else{
        	resourceService.update(resource);
        }
        mav=new ModelAndView("redirect:listUrl");
        return mav;
    }
	
	@RequestMapping(value="/editUrl/{urlId}", method=RequestMethod.GET)
    public ModelAndView editUrl(@PathVariable Long urlId, HttpServletRequest request,HttpServletResponse response) throws AcsException{
        Resource to=resourceService.getById(urlId);
        return new ModelAndView("resource/editUrl").addObject("url", to);
    }
	
	@RequestMapping("/listUrl")
    public ModelAndView roleList(ResourceCriteria criteria, HttpServletRequest request,HttpServletResponse response) throws AcsException{
		criteria.setType(Constant.RESOURCE_TYPE_URL);
		List<Resource> urls = resourceService.getResources(criteria);
        return new ModelAndView("resource/urls")
                    .addObject("urls", urls);
    }
	
	@RequestMapping(value="/delUrl/{urlId}", method=RequestMethod.GET)
	public ModelAndView delUrl(@PathVariable Long urlId, HttpServletRequest request,HttpServletResponse response)throws AcsException{
		resourceService.delete(urlId);
		ModelAndView mav=new ModelAndView("redirect:/acsAdmin/resource/listUrl");
        return mav;
    }
}
