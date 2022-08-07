package pers.codewld.iadmin.generator;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.ConstVal;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.po.LikeTable;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import org.apache.ibatis.annotations.Mapper;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashMap;
import java.util.Objects;

/**
 * MyBatis Plus Generator工具类
 */
public class GeneratorUtil {

    private static HashMap<String, Object> configuration;

    private static final String baseDir = System.getProperty("user.dir") + "/I-Admin-Generator";

    static void loadConfigurations() throws IOException {
        configuration = new Yaml().load(Files.newInputStream(Paths.get(baseDir + "/src/main/resources/config.yml")));
    }

    public static void main(String[] args) {
        try {
            loadConfigurations();

            String url = (String) configuration.get("url");
            String username = (String) configuration.get("username");
            String password = (String) configuration.get("password");
            String parent = (String) configuration.get("parent");
            Boolean isolated = (Boolean) configuration.get("isolated");

            String baseOutputDir = baseDir;
            if (Objects.equals(isolated, true)) {
                baseOutputDir += "/output";
            } else {
                baseOutputDir += "/src/main";
            }
            String JavaOutputDir = baseOutputDir + "/java";
            String XMLOutputDir = baseOutputDir + "/resources/mapper";

            FastAutoGenerator.create(url, username, password)
                    .globalConfig(builder ->
                            builder
                                    .disableOpenDir()
                                    .enableSwagger()
                                    .outputDir(JavaOutputDir))
                    .packageConfig(builder ->
                            builder
                                    .parent(parent)
                                    .entity("model.entity")
                                    .pathInfo(Collections.singletonMap(OutputFile.xml, XMLOutputDir)))
                    .strategyConfig(builder ->
                            builder
                                    .notLikeTable(new LikeTable("relation"))
                                    .controllerBuilder()
                                    .enableRestStyle()
                                    .entityBuilder()
                                    .enableLombok()
                                    .enableRemoveIsPrefix()
                                    .mapperBuilder()
                                    .mapperAnnotation(Mapper.class)
                                    .serviceBuilder()
                                    .convertServiceFileName((entityName -> entityName + ConstVal.SERVICE)))
                    .templateEngine(new FreemarkerTemplateEngine())
                    .templateConfig(builder ->
                            builder
                                    .controller("/template/controller.java")
                                    .entity("/template/entity.java")
                                    .mapper("/template/mapper.java")
                                    .xml("/template/mapper.xml")
                                    .service("/template/service.java")
                                    .serviceImpl("/template/serviceImpl.java"))
                    .execute();
        } catch (IOException e) {
            System.out.println("存在错误，请检查。");
            e.printStackTrace();
        }
    }
}
