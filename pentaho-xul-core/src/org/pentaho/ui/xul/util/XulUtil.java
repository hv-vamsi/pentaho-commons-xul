package org.pentaho.ui.xul.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.dom4j.Attribute;
import org.pentaho.ui.xul.XulDomContainer;


public final class XulUtil {
	
	public static Map <String, String> AttributesToMap(List <Attribute> attMap){
		
		Map <String, String> map = new HashMap <String, String>();
		for (int i = 0; i < attMap.size(); i++) {
			Attribute node = attMap.get(i);
			String name = node.getName();
			if(name.equals("ID")){
			  name = "id";
			}
			map.put(name, node.getValue());
		}
		return map;
		
	}
	

  public static List<org.pentaho.ui.xul.dom.Attribute> convertAttributes(List <Attribute> inList){
    List<org.pentaho.ui.xul.dom.Attribute>  outList = new ArrayList<org.pentaho.ui.xul.dom.Attribute>();
    
    for(Attribute attr : inList){
      outList.add(new org.pentaho.ui.xul.dom.Attribute(attr.getName(), attr.getValue()));
    }
    return outList;
  }
  
  public static InputStream getResourceFromClassPath(String file){
    
    //try to find localized resource
    InputStream in = XulUtil.class.getClassLoader().getResourceAsStream(XulUtil.formatResourceName(file));
    if(in == null){
      //revert to default
      in = XulUtil.class.getClassLoader().getResourceAsStream(String.format(file));
    }
    return in;
  }
  
  public static String formatResourceName(String name){
    return name.replace(".xul", "-"+Locale.getDefault().toString()+".xul"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
    
  }
  

  public static InputStream loadResourceAsStream(String src, XulDomContainer domContainer) throws FileNotFoundException{
    if(src == null || src.equals("")){
      return null;
    }
    InputStream in = null;
    try{
      in = XulUtil.class.getClassLoader().getResourceAsStream(domContainer.getXulLoader().getRootDir()+src);
      if(in == null){
        File f = new File(src);
        if(f.exists()){
          in = new FileInputStream(f);
          return in;
        }
      }
      if(in == null){
        throw new FileNotFoundException("Could not locate resoruce: "+src);
      }
      return in; 
    } finally{
      try{
        in.close();
      } catch(Exception ignored){}
    }
  }

}
