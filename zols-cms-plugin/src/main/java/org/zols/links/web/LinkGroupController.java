/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zols.links.web;

import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import static org.slf4j.LoggerFactory.getLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.zols.datatore.exception.DataStoreException;
import org.zols.links.domain.LinkGroup;
import org.zols.links.domain.Link;
import org.zols.links.service.LinkGroupService;

@RestController
@RequestMapping(value="/api/link_groups")
public class LinkGroupController {

    private static final Logger LOGGER = getLogger(LinkGroupController.class);
    
    @Autowired
    private LinkGroupService linkGroupService;    

    @RequestMapping(method = POST)    
    public LinkGroup create(@RequestBody LinkGroup group) throws DataStoreException {
        LOGGER.info("Creating new categories {}", group);
        return linkGroupService.create(group);
    }

    @RequestMapping(value = "/{name}", method = GET)    
    public Optional<LinkGroup> read(@PathVariable(value = "name") String name) throws DataStoreException {
        LOGGER.info("Getting group ", name);
        return linkGroupService.read(name);
    }

    @RequestMapping(value = "/{name}", method = PUT)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void update(@PathVariable(value = "name") String name,
            @RequestBody LinkGroup group) throws DataStoreException {        
        if (name.equals(group.getName())) {
            LOGGER.info("Updating categories with id {} with {}", name, group);
            linkGroupService.update(group);
        }
    }

    @RequestMapping(value = "/{name}", method = DELETE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable(value = "name") String name) throws DataStoreException {
        LOGGER.info("Deleting categories with id {}", name);
        linkGroupService.delete(name);
    }
    
    @RequestMapping(method = GET)    
    public List<LinkGroup> list() throws DataStoreException {
        LOGGER.info("Getting categories ");
        return linkGroupService.list();
    }
    

}
