package client.spring;

import java.util.Iterator;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.BeanDefinitionStoreException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import core.entity.Node;
import core.event.ConfigModifyEvent;

/**
 * 刷新spring管理的bean的属性值
 * @author Administrator
 *
 */
@Component("springBeanRefresh")
@Scope(value="singleton")
public class SpringBeanRefresh {
	
	@Autowired
	private SpringBeanUtils springBeanUtils;
	
	private static final Logger logger = Logger.getLogger(SpringBeanRefresh.class);
	
	public void refreshBean(ConfigModifyEvent event){
		ConfigurableApplicationContext context = (ConfigurableApplicationContext)springBeanUtils.getApplicationContext();
		updateBean(event, context);
	}
	
	/**
	 * 不能实时更新spring中的bean，暂时不用
	 * @param event
	 * @param context
	 */
	private void updateBean(ConfigModifyEvent event,ApplicationContext context){
		Node data = event.getData();
		String beanName = data.getName().substring(data.getName().lastIndexOf("/")+1, data.getName().length());
		Class<? extends Object> beanClass = context.getBean(beanName).getClass();
		BeanDefinitionBuilder beandefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(beanClass);
//		BeanDefinition beanDefinition = new RootBeanDefinition(beanClass);
//		acf.registerBeanDefinition(beanName, beanDefinition);
		ObjectMapper mapper = new ObjectMapper();
//		Object bean = context.getBean(beanName);
//		PropertyDescriptor[] pds = BeanUtils.getPropertyDescriptors(bean.getClass());
		try {
			JsonNode jsonNode = mapper.reader().readTree(data.getData());
			Iterator<String> iterator = jsonNode.getFieldNames();
			while(iterator.hasNext()){
				String field = iterator.next();
				String propertyName = field.substring(field.indexOf(".")+1, field.length());
				if(jsonNode.get(field).isBoolean()){
					beandefinitionBuilder.addPropertyValue(propertyName, jsonNode.get(field).getBooleanValue());
				}else if(jsonNode.get(field).isInt()){
					beandefinitionBuilder.addPropertyValue(propertyName, jsonNode.get(field).getIntValue());
				}else if(jsonNode.get(field).isTextual()){
					beandefinitionBuilder.addPropertyValue(propertyName, jsonNode.get(field).getTextValue());
				}else if(jsonNode.get(field).isFloatingPointNumber()){
					beandefinitionBuilder.addPropertyValue(propertyName, jsonNode.get(field).getDoubleValue());
				}else if(jsonNode.get(field).isLong()){
					beandefinitionBuilder.addPropertyValue(propertyName, jsonNode.get(field).getLongValue());
				}else if(jsonNode.get(field).isBigDecimal()){
					beandefinitionBuilder.addPropertyValue(propertyName, jsonNode.get(field).getBigIntegerValue());
				}
			}
			ConfigurableApplicationContext configurableApplicationContext = (ConfigurableApplicationContext) context;
	        BeanDefinitionRegistry beanDefinitonRegistry = (BeanDefinitionRegistry) configurableApplicationContext
	                .getBeanFactory();
	        beanDefinitonRegistry.registerBeanDefinition(beanName, beandefinitionBuilder.getRawBeanDefinition());
	        logger.debug("bean.age="+context.getBean(beanName));
		}catch(BeanDefinitionStoreException e){
			logger.error(e.getMessage(), e);
		}catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
		}
	}
	
}

