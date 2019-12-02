package lab2.practice;

import org.springframework.beans.BeansException;
import org.springframework.beans.PropertyValue;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.TypedStringValue;
import org.springframework.stereotype.Component;

@Component
public class ReplaceMessageBeanFactoryPostProcessor implements BeanFactoryPostProcessor {

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        for (String beanDefinitionName : beanFactory.getBeanDefinitionNames()) {
            BeanDefinition beanDefinition = beanFactory.getBeanDefinition(beanDefinitionName);
            if (beanDefinition.getBeanClassName().equals(MessagePrinter.class.getName())) {
                for (PropertyValue propertyValue : beanDefinition.getPropertyValues().getPropertyValues()) {
                    if(propertyValue.getName().equals("message")){
                        TypedStringValue typedStringValue = (TypedStringValue)propertyValue.getValue();
                        propertyValue.setConvertedValue(PropertyRepository.get(typedStringValue.getValue()));
                    }
                }
            }
        }
    }
}
