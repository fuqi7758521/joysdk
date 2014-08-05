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

import com.joysdk.acs.criteria.RoleCriteria;
import com.joysdk.acs.exception.AcsException;
import com.joysdk.acs.service.RoleService;
import com.joysdk.common.acs.model.Role;
import com.joysdk.common.util.RequestUtil;

@Controller
@RequestMapping("/acsAdmin/role")
public class RoleController {

	@Autowired
	private RoleService roleService;
	
	@RequestMapping("/add")
    public ModelAndView addRole(HttpServletRequest request,HttpServletResponse response) throws AcsException{
        return new ModelAndView("role/editRole");
    }
	
	@RequestMapping("/save")
    public ModelAndView saveRole(Role role, HttpServletRequest request,HttpServletResponse response) throws AcsException{
        ModelAndView view=new ModelAndView("role/editRole");
        
        String name=RequestUtil.getString(request, "name");
        
        if(StringUtils.isEmpty(name)){
            return view.addObject("errorMsg", "请填写角色名称"); 
        }
        if(role.getId() == null){
        	roleService.add(role);
        }else{
        	roleService.update(role);
        }
        view=new ModelAndView("redirect:list");
        return view;
    }
	
	@RequestMapping(value="/edit/{roleId}", method=RequestMethod.GET)
    public ModelAndView editRole(@PathVariable Long roleId, HttpServletRequest request,HttpServletResponse response) throws AcsException{
        Role role=roleService.getById(roleId);
        return new ModelAndView("role/editRole").addObject("role", role);
    }
	
	@RequestMapping("/list")
    public ModelAndView roleList(RoleCriteria criteria, HttpServletRequest request,HttpServletResponse response) throws AcsException{
		List<Role> roles = roleService.getRoles(criteria);
        return new ModelAndView("role/roles").addObject("roles", roles);
    }
	 
}
