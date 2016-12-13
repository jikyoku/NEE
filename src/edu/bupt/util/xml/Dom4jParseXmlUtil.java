package edu.bupt.util.xml;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 * Created by shixu on 2016/11/30.
 */
public class Dom4jParseXmlUtil {
    public void parseXml01() {
        try {
            String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                    "<modules id=\"123\" name=\"heheda\">\n" +
                    "    <module>这个是module标签的文本信息</module>\n" +
                    "    <module>这个是module标签的文本信息0-----</module>\n" +
                    "</modules>";
            Document document = DocumentHelper.parseText(xml);//必须指定文件的绝对路径
            Element rootElement = document.getRootElement();
            System.out.println("根节点名称：" + rootElement.getName());//获取节点的名称
            System.out.println("根节点有多少属性：" + rootElement.attributeCount());//获取节点属性数目
            System.out.println("根节点id属性的值：" + rootElement.attributeValue("id"));//获取节点的属性id的值
            System.out.println("根节点name属性的值：" +rootElement.attributeValue("name"));
            System.out.println("根节点内文本：" + rootElement.getText());//如果元素有子节点则返回空字符串，否则返回节点内的文本
            System.out.println("根节点内文本(1)：" + rootElement.getTextTrim());//去掉的是标签与标签之间的tab键和换行符等等，不是内容前后的空格
            System.out.println("根节点子节点文本内容：" + rootElement.getStringValue().trim()); //返回当前节点递归所有子节点的文本信息。
            //获取子节点
            Element element = rootElement.element("module");
            if (element != null) {
                System.out.println("子节点的文本：" + element.getText());//因为子节点和根节点都是Element对象所以它们的操作方式都是相同的
            }
            //但是有些情况xml比较复杂，规范不统一，某个节点不存在直接java.lang.NullPointerException，所以获取到element对象之后要先判断一下是否为空
            rootElement.setName("root");//支持修改节点名称
            System.out.println("根节点修改之后的名称：" + rootElement.getName());
            rootElement.setText("text"); //同样修改标签内的文本也一样
            System.out.println("根节点修改之后的文本：" + rootElement.getText());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Dom4jParseXmlUtil demo = new Dom4jParseXmlUtil();
        demo.parseXml01();
    }
}