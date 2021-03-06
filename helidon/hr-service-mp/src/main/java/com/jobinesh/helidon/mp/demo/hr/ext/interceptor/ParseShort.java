/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jobinesh.helidon.mp.demo.hr.ext.interceptor;

import org.supercsv.cellprocessor.CellProcessorAdaptor;
import org.supercsv.cellprocessor.ift.LongCellProcessor;
import org.supercsv.cellprocessor.ift.StringCellProcessor;
import org.supercsv.exception.SuperCsvCellProcessorException;
import org.supercsv.util.CsvContext;


/**
 *
 * Third party source, used for defining  field in 
 * while converting between Java to  CSV
 *
 */
public class ParseShort extends CellProcessorAdaptor implements StringCellProcessor {
    
    public ParseShort() {
        super();
    }
    
    public ParseShort(final LongCellProcessor next) {
        super(next);
    }
    
    @Override
    public Object execute(final Object value, final CsvContext context) {
        
        validateInputNotNull(value, context);
        
        final Short result;
        if( value instanceof Short ) {
            result = (Short) value;
            
        } else if( value instanceof String ) {
            try {
                result = Short.valueOf((String) value);
            } catch(final NumberFormatException e) {
                throw new SuperCsvCellProcessorException(String.format("'%s' could not be parsed as an Short", value),
                    context, this, e);
            }
        } else {
            final String actualClassName = value.getClass().getName();
            throw new SuperCsvCellProcessorException(String.format(
                "the input value should be of type Short or String but is of type %s", actualClassName), context, this);
        }
        
        return next.execute(result, context);
    }
}
