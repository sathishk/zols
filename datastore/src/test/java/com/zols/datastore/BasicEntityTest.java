package com.zols.datastore;

import com.zols.datastore.config.DataStoreConfiguration;
import com.zols.datastore.domain.Attribute;
import com.zols.datastore.domain.Entity;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration(classes = {DataStoreConfiguration.class})
@RunWith(SpringJUnit4ClassRunner.class)
public class BasicEntityTest {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(BasicEntityTest.class);

    @Autowired
    private DataStore dataStore;

    /**
     * Basic Entity to test
     */
    private Entity entity;

    @Before
    public void beforeTest() {
        entity = getBasicEntity();
        dataStore.create(entity, Entity.class);
    }

    @Test
    public void testBasicEntityCreate() {
        LOGGER.info("testing Entity Create", entity.getName());
        Assert.assertTrue("basic entity should have name", entity.getName() != null);
        LOGGER.info("tested Entity Create", entity.getName());
    }

    @Test
    public void testBasicEntityRead() {
        LOGGER.info("testing Entity Read", entity.getName());
        Entity entityResult = dataStore.read(entity.getName(), Entity.class);
        Assert.assertEquals("basic entity has to be present with Label 'Basic Entity'", "Basic Entity", entityResult.getLabel());
        LOGGER.info("tested Entity Read", entity.getName());
    }

    @Test
    public void testSearchEntity() {
        LOGGER.info("testing Entity Read", entity.getName());
        Entity searchEntity = new Entity();
        searchEntity.setName(entity.getName());
        List entitiesResult = dataStore.listByExample(searchEntity);
        Assert.assertEquals("There should be one Entity in the db with name " + entity.getName(), 1, entitiesResult.size());
        LOGGER.info("tested Entity Read", entity.getName());
    }

    @Test
    public void testSearchAndRemove() {
        LOGGER.info("testing Entity Read", entity.getName());
        Entity searchEntity = new Entity();
        searchEntity.setName(entity.getName());
        dataStore.deleteByExample(searchEntity);
        List entitiesResult = dataStore.listByExample(searchEntity);
        Assert.assertEquals("There should be one Entity in the db with name " + entity.getName(), 0, entitiesResult.size());
        LOGGER.info("tested Entity Read", entity.getName());
    }

    @After
    public void afterTest() {
        LOGGER.info("Deleting 'basic' Entity", entity.getName());
        dataStore.delete(entity.getName(), Entity.class);
        LOGGER.info("Deleted 'basic' Entity", entity.getName());
    }

    /**
     * Entity Object with which we conduct all basic test cases.
     *
     * @return
     */
    private Entity getBasicEntity() {
        Entity entity = new Entity();
        //entity.setName("Basic");
        entity.setLabel("Basic Entity");
        entity.setDescription("Describe an Basic Entity");
        Attribute attribute = null;
        List<Attribute> attributes = new ArrayList<Attribute>(1);
        attribute = new Attribute();
        attribute.setDescription("Count is an Integer");
        attribute.setName("count");
        attribute.setType("Integer");
        attributes.add(attribute);
        entity.setAttributes(attributes);
        return entity;
    }

}
