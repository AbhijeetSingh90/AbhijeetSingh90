package com.school.teacher.controller;

import com.school.teacher.serviceImpl.DataStateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class DataStateController {

    @Autowired
    public DataStateService dataStateService;

    @RequestMapping(method = RequestMethod.GET, path = "/rest/sections/{context}/{reference}/incomplete")
    public List<String> getIncompleteSectionNames(@PathVariable("context") String context,
                                                  @PathVariable("reference") String reference,
                                                  @RequestParam("path") String path) {
        return dataStateService.getIncompleteSections(context, reference, path.split("->"));
    }
}
