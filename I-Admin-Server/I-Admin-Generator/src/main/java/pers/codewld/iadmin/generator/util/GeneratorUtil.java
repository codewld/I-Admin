package pers.codewld.iadmin.generator.util;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.ConstVal;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.po.LikeTable;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.baomidou.mybatisplus.generator.fill.Column;
import org.apache.ibatis.annotations.Mapper;

import java.util.Collections;
import java.util.Scanner;

/**
 * MyBatis Plus Generator 工具类
 */
public class GeneratorUtil {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入数据库连接URL： [jdbc:mysql://localhost:3306/i-admin]");
        String url = scanner.nextLine();
        System.out.println("请输入数据库用户名： [root]");
        String username = scanner.nextLine();
        System.out.println("请输入数据库密码： [root]");
        String password = scanner.nextLine();
        System.out.println("请输入父包名： [pers.codewld.iadmin.api]");
        String parent = scanner.nextLine();
        scanner.close();

        String baseOutputDir = System.getProperty("user.dir") + "/I-Admin-Generator/output";
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
                                .enableHyphenStyle()
                                .enableRestStyle()
                                .entityBuilder()
                                .enableLombok()
                                .addTableFills(new Column("create_time", FieldFill.INSERT))
                                .addTableFills(new Column("update_time", FieldFill.INSERT_UPDATE))
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
    }
}