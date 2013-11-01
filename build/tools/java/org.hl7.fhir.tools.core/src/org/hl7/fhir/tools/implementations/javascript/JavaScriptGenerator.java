package org.hl7.fhir.tools.implementations.javascript;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.hl7.fhir.definitions.model.Definitions;
import org.hl7.fhir.definitions.model.ResourceDefn;
import org.hl7.fhir.tools.implementations.BaseGenerator;
import org.hl7.fhir.tools.publisher.PlatformGenerator;
import org.hl7.fhir.utilities.Logger;
import org.stringtemplate.v4.ST;

public class JavaScriptGenerator extends BaseGenerator implements PlatformGenerator {
  
  public static char SEPARATOR = File.separatorChar;

  @Override
  public String getName() {
    return "javascript";
  }

  @Override
  public String getTitle() {
    return "JavaScript";
  }

  @Override
  public String getDescription() {
    return "Generates Mongoose models for FHIR resources";
  }

  @Override
  public boolean isECoreGenerator() {
    return false;
  }

  @Override
  public void generate(Definitions definitions, String destDir, String implDir, String version, Date genDate, Logger logger, String svnRevision)
      throws Exception {
    String baseDir = implDir + SEPARATOR + "app";
    String javaScriptRoot = implDir + SEPARATOR + ".." + SEPARATOR + ".." + SEPARATOR 
        + "tools" + SEPARATOR + "javascript" + SEPARATOR;
    
    File modelDir = new File(baseDir + SEPARATOR + "models");
    if (! modelDir.exists()) {
      modelDir.mkdirs();
    }
    File controllerDir = new File(baseDir + SEPARATOR + "controllers");
    if (! controllerDir.exists()) {
      controllerDir.mkdirs();
    }
    File configDir = new File(implDir + SEPARATOR + "config");
    if (! configDir.exists()) {
      configDir.mkdirs();
    }
    File libDir = new File(implDir + SEPARATOR + "lib");
    if (! libDir.exists()) {
      libDir.mkdirs();
    }
    String genericControllerTemplate = FileUtils.readFileToString(new File(javaScriptRoot + "app" + SEPARATOR + "controllers" + SEPARATOR + "generic_controller.js.st"));
    Map<String, ResourceDefn> namesAndDefinitions = definitions.getResources();
    for (String name : namesAndDefinitions.keySet()) {
      generateMongooseModel(name, modelDir, definitions);
      generateExpressController(name, controllerDir, genericControllerTemplate);
    }
    
    
    File resourceHistoryModel = new File(javaScriptRoot + "app" + SEPARATOR + "models" + SEPARATOR + "resource_history.js");
    FileUtils.copyFileToDirectory(resourceHistoryModel, modelDir);
    FileUtils.copyFileToDirectory(new File(javaScriptRoot + SEPARATOR + "package.json"), new File(implDir));
    FileUtils.copyFileToDirectory(new File(javaScriptRoot + SEPARATOR + "app.js"), new File(implDir));
    FileUtils.copyFileToDirectory(new File(javaScriptRoot + SEPARATOR + "config" + SEPARATOR + "mongoose.js"), configDir);
    FileUtils.copyFileToDirectory(new File(javaScriptRoot + SEPARATOR + "lib" + SEPARATOR + "format_query_parameter_handler.js"), libDir);
    FileUtils.copyFileToDirectory(new File(javaScriptRoot + SEPARATOR + "lib" + SEPARATOR + "response_format_helper.js"), libDir);
  }
  
  private void generateMongooseModel(String name, File modelDir, Definitions definitions) throws Exception {
    File modelFile = new File(modelDir.getPath() + SEPARATOR + name.toLowerCase() + ".js");
    MongooseModel model = new MongooseModel(name, definitions, modelFile);
    model.generate();
  }
  
  private void generateExpressController(String name, File controllerDir, String genericControllerTemplate) throws IOException {
    File controllerFile = new File(controllerDir.getPath() + SEPARATOR + name.toLowerCase() + ".js");
    ST controllerTemplate = new ST(genericControllerTemplate);
    
    controllerTemplate.add("ModelName", name);
    controllerTemplate.add("LowerCaseModelName", name.toLowerCase());
    
    Writer controllerWriter = new BufferedWriter(new FileWriter(controllerFile));
    controllerWriter.write(controllerTemplate.render());
    controllerWriter.flush();
    controllerWriter.close();
  }

  @Override
  public void generate(org.hl7.fhir.definitions.ecore.fhir.Definitions definitions, String destDir, String implDir, Logger logger) throws Exception {
    // TODO Auto-generated method stub

  }

  @Override
  public boolean doesCompile() {
    return false;
  }

  @Override
  public boolean compile(String rootDir, List<String> errors) throws Exception {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public boolean doesTest() {
    return false;
  }

  @Override
  public void loadAndSave(String rootDir, String sourceFile, String destFile) throws Exception {
    // TODO Auto-generated method stub

  }

  @Override
  public String checkFragments(String rootDir, String fragmentsXml) throws Exception {
    // TODO Auto-generated method stub
    return null;
  }

}
