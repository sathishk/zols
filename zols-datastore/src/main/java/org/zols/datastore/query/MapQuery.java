/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zols.datastore.query;

import com.github.rutledgepaulv.qbuilders.builders.QBuilder;
import com.github.rutledgepaulv.qbuilders.properties.concrete.StringProperty;

/**
 *
 * @author sathish
 */
public class MapQuery extends QBuilder<MapQuery> {

    public static StringProperty<MapQuery> stringFiled(String name) {
        return new MapQuery().string(name);
    }

}