/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zols.templates.service;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import org.zols.datastore.DataStore;
import org.slf4j.Logger;
import static org.slf4j.LoggerFactory.getLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.zols.datastore.service.DataService;
import org.zols.datatore.exception.DataStoreException;
import org.zols.links.service.LinkService;
import org.zols.templates.domain.PageRequest;
import org.zols.templates.domain.Page;
import org.zols.templates.domain.Template;

/**
 *
 * @author sathish_ku
 */
@Service
public class PageService {

    private static final Logger LOGGER = getLogger(PageService.class);

    @Autowired
    private DataService dataService;
    
    @Autowired
    private LinkService linkService;
    
    @Autowired
    private DataStore dataStore;

    @Autowired
    private TemplateService templateService;
    
    /**
     * Creates a new Page with given Object
     *
     * @param pageRequest Object to be Create
     * @return created Page object
     */
    @Secured("ROLE_ADMIN")
    public Page create(PageRequest pageRequest,Locale loc) throws DataStoreException {
        Page createdPage = null;
        if (pageRequest != null) {
            
            Map<String,Object> createdData = dataService.create(pageRequest.getTemplate().getDataType(), pageRequest.getData(),loc);
            
            String dataName = createdData.get(dataService.getIdField(pageRequest.getTemplate().getDataType())).toString();
            
            Page page = new Page();
            page.setDataName(dataName);
            page.setTemplateName(pageRequest.getTemplate().getName());
            
            createdPage = dataStore.getObjectManager(Page.class).create(page);
            LOGGER.info("Created Page {}", createdPage.getName());
            
            linkService.linkUrl(pageRequest.getLinkName(), "/pages/"+createdPage.getName());
        }
        return createdPage;
    }
    
    /**
     * Get the Page Request with given String
     *
     * @param pageName String to be Search
     * @return searched Page
     */
    public PageRequest readRequest(String pageName,Locale loc) throws DataStoreException {
        LOGGER.info("Reading Page Request {}", pageName);
        PageRequest pageRequest;
        Optional<Page> page = read(pageName);
        if(page.isPresent()) {
            pageRequest = new PageRequest();
            Optional<Template> template = templateService.read(page.get().getTemplateName());
            pageRequest.setTemplate(template.get());
            pageRequest.setData(dataService.read(template.get().getDataType(), page.get().getDataName(),loc).get());
            return pageRequest;
        }
        return null;
    }

    /**
     * Get the Page with given String
     *
     * @param pageName String to be Search
     * @return searched Page
     */
    public Optional<Page> read(String pageName) throws DataStoreException {
        LOGGER.info("Reading Page {}", pageName);
        return dataStore.getObjectManager(Page.class).read(pageName);
    }

    /**
     * Update a Page with given Object
     *
     * @param page Object to be update
     * @return returns the status of the Update Operation
     */
    @Secured("ROLE_ADMIN")
    public Page update(Page page) throws DataStoreException {
        Page updated = null;
        if (page != null) {
            LOGGER.info("Updating Page {}", page);
            updated = dataStore.getObjectManager(Page.class).update(page,page.getName());
        }
        return updated;
    }

    /**
     * Delete a Page with given String
     *
     * @param pageName String to be delete
     * @return status of the delete operation
     */
    @Secured("ROLE_ADMIN")
    public Boolean delete(String pageName) throws DataStoreException {
        LOGGER.info("Deleting Page {}", pageName);
        return dataStore.getObjectManager(Page.class).delete(pageName);
    }

    /**
     * List all Pages
     *
     * @return list of all pages
     */
    public List<Page> list() throws DataStoreException {
        LOGGER.info("Getting Pages ");
        return dataStore.getObjectManager(Page.class).list();
    }
    
 
}
