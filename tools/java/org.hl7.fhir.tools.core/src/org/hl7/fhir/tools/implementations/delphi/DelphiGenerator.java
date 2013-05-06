package org.hl7.fhir.tools.implementations.delphi;
/*
Copyright (c) 2011-2013, HL7, Inc
All rights reserved.

Redistribution and use in source and binary forms, with or without modification, 
are permitted provided that the following conditions are met:

 * Redistributions of source code must retain the above copyright notice, this 
   list of conditions and the following disclaimer.
 * Redistributions in binary form must reproduce the above copyright notice, 
   this list of conditions and the following disclaimer in the documentation 
   and/or other materials provided with the distribution.
 * Neither the name of HL7 nor the names of its contributors may be used to 
   endorse or promote products derived from this software without specific 
   prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND 
ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED 
WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. 
IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, 
INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT 
NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR 
PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, 
WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) 
ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE 
POSSIBILITY OF SUCH DAMAGE.

*/
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hl7.fhir.definitions.Config;
import org.hl7.fhir.definitions.model.BindingSpecification;
import org.hl7.fhir.definitions.model.DefinedCode;
import org.hl7.fhir.definitions.model.DefinedStringPattern;
import org.hl7.fhir.definitions.model.Definitions;
import org.hl7.fhir.definitions.model.ElementDefn;
import org.hl7.fhir.definitions.model.PrimitiveType;
import org.hl7.fhir.definitions.model.ResourceDefn;
import org.hl7.fhir.definitions.model.SearchParameter;
import org.hl7.fhir.definitions.model.TypeRef;
import org.hl7.fhir.tools.implementations.BaseGenerator;
import org.hl7.fhir.tools.implementations.GeneratorUtils;
import org.hl7.fhir.tools.publisher.PlatformGenerator;
import org.hl7.fhir.utilities.Logger;
import org.hl7.fhir.utilities.Utilities;
import org.hl7.fhir.utilities.ZipGenerator;

/**
 * Generates the delphi reference implementation
 * 
 * todo: the delphi reference implementation depends on too much HL7Connect infrastructure.
 * 
 * @author Grahame
 *
 */
public class DelphiGenerator extends BaseGenerator implements PlatformGenerator {

  public enum ClassCategory {
    Type, Component, Resource
  }

  private DelphiCodeGenerator defCodeType;
  private DelphiCodeGenerator defCodeComp;
  private DelphiCodeGenerator defCodeRes;
  private DelphiCodeGenerator prsrCode;
  private Definitions definitions;
  
  private Map<ElementDefn, String> typeNames = new HashMap<ElementDefn, String>();
  private List<String> enumsDone = new ArrayList<String>();

  private List<ElementDefn> enums = new ArrayList<ElementDefn>();
  private List<String> enumNames = new ArrayList<String>();
  private List<ElementDefn> strucs  = new ArrayList<ElementDefn>();
  private List<String> lists = new ArrayList<String>();
  

  private StringBuilder workingParserX;
  private StringBuilder workingComposerX;
  private StringBuilder workingParserJ;
  private StringBuilder workingComposerJ;
  private StringBuilder factoryIntf;
  private StringBuilder factoryImpl;
  
  private StringBuilder prsrRegX = new StringBuilder();
  private StringBuilder srlsRegX = new StringBuilder();
  private StringBuilder prsrRegJ = new StringBuilder();
  private StringBuilder srlsRegJ = new StringBuilder();
  private StringBuilder prsrImpl = new StringBuilder();
  private StringBuilder prsrdefX = new StringBuilder();
  private StringBuilder srlsdefX = new StringBuilder();
  private StringBuilder prsrdefJ = new StringBuilder();
  private StringBuilder srlsdefJ = new StringBuilder();
  private Map<String, String> simpleTypes = new HashMap<String, String>();
  
  @Override
public void generate(Definitions definitions, String destDir, String implDir, String version, Date genDate, Logger logger)  throws Exception {
    start(implDir, version, genDate);
    initParser(version, genDate);
    
    this.definitions = definitions;
    
    generateElement();
    parserGap();
    generatePrimitive(new DefinedCode("enum", "", ""), "TFhirElement", true, false);
    
    for (DefinedCode n : definitions.getPrimitives().values()) {
      if (n instanceof PrimitiveType)
        generatePrimitive(n, "TFhirType", false, false);
    }
    for (DefinedCode n : definitions.getPrimitives().values()) {
      if (!(n instanceof PrimitiveType))
        generatePrimitive(n, ((DefinedStringPattern) n).getBase().contains(" ") ? "TFhirType" : "TFhir"+Utilities.capitalize(((DefinedStringPattern) n).getBase()), false, true);
    }
    parserGap();
    
    generateResource();
    parserGap();
    
    for (ElementDefn n : definitions.getInfrastructure().values()) {
      if (n.getName().equals("Extension"))
        generate(n, "TFHIRObject", true, false, ClassCategory.Type);
      else
        generate(n, "TFhirElement", true, false, ClassCategory.Type);
    }
    for (ElementDefn n : definitions.getTypes().values()) {
      generate(n, "TFhirType", false, false, ClassCategory.Type);
    }
    
    for (ElementDefn n : definitions.getStructures().values()) {
      generate(n, "TFhirType", false, false, ClassCategory.Type);
    }
    
    for (DefinedCode c : definitions.getConstraints().values()) {
      genConstraint(c);
    }
    parserGap();
    for (String s : definitions.sortedResourceNames()) {
      ResourceDefn n = definitions.getResources().get(s);
      generate(n.getRoot(), "TFhirResource", true, true, ClassCategory.Resource);
      genResource(n, "TFhir"+n.getName(), "TFhirResource", true, ClassCategory.Resource);
      prsrRegX.append("  else if element.baseName = '"+n.getName()+"' Then\r\n    result := Parse"+n.getName()+"(element)\r\n");
      srlsRegX.append("    frt"+n.getName()+": Compose"+n.getName()+"(xml, '"+n.getName()+"', TFhir"+n.getName()+"(resource));\r\n");
      prsrRegJ.append("  else if json.ItemName = '"+n.getName()+"' Then\r\n    result := Parse"+n.getName()+"\r\n");
      srlsRegJ.append("    frt"+n.getName()+": Compose"+n.getName()+"(json, '"+n.getName()+"', TFhir"+n.getName()+"(resource));\r\n");
    }
    
//    for (String n : ini.getPropertyNames("future-resources")) {
//      ElementDefn e = new ElementDefn();
//      e.setName(ini.getStringProperty("future-resources", n));
//      generate(e, definitions.getConceptDomains());
//    }

    defCodeRes.enumConsts.add("  FHIR_GENERATED_VERSION = '"+version+"';\r\n");
    defCodeRes.enumConsts.add("  FHIR_GENERATED_DATE = '"+new SimpleDateFormat("yyyyMMddHHmmss").format(genDate)+"';\r\n");
    defCodeRes.classDefs.add(" TFhirResourceFactory = class (TFHIRBaseFactory)\r\n  public\r\n"+factoryIntf.toString()+"  end;\r\n");
    defCodeRes.classImpls.add(factoryImpl.toString());
    defCodeComp.finish();
    defCodeType.finish();
    defCodeRes.finish();

    prsrCode.classDefs.add(buildParser());
    prsrCode.classImpls.add(prsrImpl.toString());
    prsrCode.finish();
    
    ZipGenerator zip = new ZipGenerator(destDir+"delphi.zip");
    zip.addFiles(implDir, "", ".pas");
    zip.close();    
  }

  private void parserGap() {
    prsrdefX.append("\r\n");
    prsrdefJ.append("\r\n");
    srlsdefX.append("\r\n");
    srlsdefJ.append("\r\n");
  }

  private void start(String implDir, String version, Date genDate)
      throws UnsupportedEncodingException, FileNotFoundException, Exception {
    defCodeRes = new DelphiCodeGenerator(new FileOutputStream(implDir+"FHIRResources.pas"));
    defCodeRes.start();
    defCodeRes.name = "FHIRResources";
    defCodeRes.comments.add("FHIR v"+version+" generated "+Config.DATE_FORMAT().format(genDate));
    defCodeRes.precomments.add("!Wrapper uses FHIRBase, FHIRBase_Wrapper, FHIRTypes, FHIRTypes_Wrapper, FHIRComponents, FHIRComponents_Wrapper");
    defCodeRes.precomments.add("!ignore ALL_RESOURCE_TYPES");
    defCodeRes.uses.add("SysUtils");
    defCodeRes.uses.add("Classes");
    defCodeRes.uses.add("StringSupport");
    defCodeRes.uses.add("DecimalSupport");
    defCodeRes.uses.add("AdvBuffers");
    defCodeRes.uses.add("MSSEWrap");
    defCodeRes.uses.add("FHIRBase");
    defCodeRes.uses.add("FHIRTypes");
    defCodeRes.uses.add("FHIRComponents");

    defCodeType = new DelphiCodeGenerator(new FileOutputStream(implDir+"FHIRTypes.pas"));
    defCodeType.start();
    defCodeType.name = "FHIRTypes";
    defCodeType.comments.add("FHIR v"+version+" generated "+Config.DATE_FORMAT().format(genDate));
    defCodeType.precomments.add("!Wrapper uses FHIRBase, FHIRBase_Wrapper");
    defCodeType.uses.add("Classes");
    defCodeType.uses.add("SysUtils");
    defCodeType.uses.add("DecimalSupport");
    defCodeType.uses.add("StringSupport");
    defCodeType.uses.add("AdvWideStringLists");
    defCodeType.uses.add("AdvBuffers");
    defCodeType.uses.add("MSSEWrap");
    defCodeType.uses.add("FHIRBase");

    defCodeComp = new DelphiCodeGenerator(new FileOutputStream(implDir+"FHIRComponents.pas"));
    defCodeComp.start();
    defCodeComp.name = "FHIRComponents";
    defCodeComp.comments.add("FHIR v"+version+" generated "+Config.DATE_FORMAT().format(genDate));
    defCodeComp.precomments.add("!Wrapper uses FHIRBase, FHIRBase_Wrapper, FHIRTypes, FHIRTypes_Wrapper");
    defCodeComp.uses.add("SysUtils");
    defCodeComp.uses.add("Classes");
    defCodeComp.uses.add("StringSupport");
    defCodeComp.uses.add("DecimalSupport");
    defCodeComp.uses.add("AdvBuffers");
    defCodeComp.uses.add("MSSEWrap");
    defCodeComp.uses.add("FHIRBase");
    defCodeComp.uses.add("FHIRTypes");

    factoryIntf = new StringBuilder();
    factoryImpl = new StringBuilder();

    
    prsrCode = new DelphiCodeGenerator(new FileOutputStream(implDir+"FHIRParser.pas"));
    prsrCode.start();
    prsrCode.name = "FHIRParser";
  }

  private void generate(ElementDefn root, String superClass, boolean listsAreWrapped, boolean resource, ClassCategory category) throws Exception {
    typeNames.clear();
    enums.clear();
    strucs.clear();
    enumNames.clear();
        
    for (ElementDefn e : root.getElements()) {
      if (!root.typeCode().equals("Resource") || (!e.getName().equals("extension") && !e.getName().equals("text")))
        scanNestedTypes(root, root.getName(), e);
    }

    for (ElementDefn e : enums) {
      generateEnum(e);
    }
    for (ElementDefn e : strucs) {
      generateType(e, listsAreWrapped, category == ClassCategory.Resource ? ClassCategory.Component : category);
    }
   
    if (root.getTypes().size() > 0 && root.getTypes().get(0).getName().equals("GenericType")) {
      for (TypeRef td : definitions.getKnownTypes()) {
        if (td.getName().equals(root.getName()) && td.hasParams()) {
          for (String pt : td.getParams()) {
            String tn = getTypeName(pt, false);
            if (tn.equals(pt))
              tn = "TFhir"+tn;
            genGenericResource(root, "TFhir"+root.getName()+"_"+getTitle(pt), tn, superClass, ClassCategory.Type);
          }
        }
      }
    } else if (!resource) {
      genType(root, "TFhir"+root.getName(), superClass, listsAreWrapped, category);
    }
  }
    
  private void genGenericResource(ElementDefn root, String tn, String pt, String superClass, ClassCategory category) throws Exception {
    prsrdefX.append("    function Parse"+tn.substring(5)+"(element : IXmlDomElement) : "+tn+";\r\n");
    srlsdefX.append("    procedure Compose"+tn.substring(5)+"(xml : TMsXmlBuilder; name : string; elem : "+tn+");\r\n");
    prsrdefJ.append("    function Parse"+tn.substring(5)+" : "+tn+";\r\n");
    srlsdefJ.append("    procedure Compose"+tn.substring(5)+"(json : TJSONWriter; name : string; elem : "+tn+");\r\n");
    workingParserX = new StringBuilder();
    workingComposerX = new StringBuilder();
    workingParserJ = new StringBuilder();
    workingComposerJ = new StringBuilder();
    
    StringBuilder def = new StringBuilder();
    StringBuilder defPriv1 = new StringBuilder();
    StringBuilder defPriv2 = new StringBuilder();
    StringBuilder defPub = new StringBuilder();
    StringBuilder impl = new StringBuilder();
    StringBuilder create = new StringBuilder();
    StringBuilder destroy = new StringBuilder();
    StringBuilder assign = new StringBuilder();
    StringBuilder getkids = new StringBuilder();
    StringBuilder getprops = new StringBuilder();
      

    
    for (ElementDefn e : root.getElements()) {
      generateField(e, defPriv1, defPriv2, defPub, impl, create, destroy, assign, getkids, getprops, tn, pt, true, false, category);
    }

    def.append("  {@Class "+tn+" : "+superClass+"\r\n");
    def.append("    "+Utilities.normaliseEolns(root.getDefinition())+"\r\n");
    def.append("  }\r\n");
    def.append("  {!.Net HL7Connect.Fhir."+tn.substring(5)+"}\r\n");
    def.append("  "+tn+" = class ("+superClass+")\r\n");
    factoryIntf.append("    {@member new"+tn.substring(5)+"\r\n      create a new "+root.getName()+"\r\n    }\r\n    {!script nolink}\r\n    function new"+tn.substring(5)+" : "+tn+";\r\n");
    factoryImpl.append("function TFhirResourceFactory.new"+tn.substring(5)+" : "+tn+";\r\nbegin\r\n  result := "+tn+".create;\r\nend;\r\n\r\n");
    def.append("  private\r\n");
    def.append(defPriv1.toString());
    def.append(defPriv2.toString());
    def.append("  protected\r\n");
    def.append("    Procedure GetChildrenByName(child_name : string; list : TFHIRObjectList); override;\r\n");
    def.append("  public\r\n");
    def.append("    constructor Create; Override;\r\n");
    def.append("    destructor Destroy; override;\r\n");
    def.append("    {!script hide}\r\n");
    def.append("    procedure Assign(oSource : TAdvObject); override;\r\n");
    def.append("    function Link : "+tn+"; overload;\r\n");
    def.append("    function Clone : "+tn+"; overload;\r\n");
    def.append("    {!script show}\r\n");
    def.append("  published\r\n");
    def.append(defPub.toString());
    def.append("  end;\r\n");
    def.append("\r\n");

    StringBuilder impl2 = new StringBuilder();
    impl2.append("{ "+tn+" }\r\n\r\n");
    impl2.append("constructor "+tn+".Create;\r\n");
    impl2.append("begin\r\n");
    impl2.append("  inherited;\r\n");
    impl2.append(create.toString());
    impl2.append("end;\r\n\r\n");

    impl2.append("destructor "+tn+".Destroy;\r\n");
    impl2.append("begin\r\n");
    impl2.append(destroy.toString());
    impl2.append("  inherited;\r\n");
    impl2.append("end;\r\n\r\n");
    
    impl2.append("procedure "+tn+".Assign(oSource : TAdvObject);\r\n");
    impl2.append("begin\r\n");
    impl2.append("  inherited;\r\n");
    impl2.append(assign.toString());
    impl2.append("end;\r\n\r\n");
    
    impl2.append("procedure "+tn+".GetChildrenByName(child_name : string; list : TFHIRObjectList);\r\n");
    impl2.append("begin\r\n");
    impl2.append("  inherited;\r\n");
    impl2.append(getkids.toString());
    impl2.append("end;\r\n\r\n");
    
    impl2.append("function "+tn+".Link : "+tn+";\r\n");
    impl2.append("begin\r\n");
    impl2.append("  result := "+tn+"(inherited Link);\r\n");
    impl2.append("end;\r\n\r\n");
    impl2.append("function "+tn+".Clone : "+tn+";\r\n");
    impl2.append("begin\r\n");
    impl2.append("  result := "+tn+"(inherited Clone);\r\n");
    impl2.append("end;\r\n\r\n");
    
    getCode(category).classDefs.add(def.toString());
    getCode(category).classImpls.add(impl2.toString()+impl.toString());
    getCode(category).classFwds.add("  "+tn+" = class;\r\n");
    generateParser(tn, false, !superClass.equals("TFHIRObject"));
  }
  
  private DelphiCodeGenerator getCode(ClassCategory category) {
    switch (category) {
    case Type:
      return defCodeType;
    case Component:
      return defCodeComp;
    case Resource:
      return defCodeRes;
    }
    return null;
  }

  private void genType(ElementDefn root, String tn, String superClass, boolean listsAreWrapped, ClassCategory category) throws Exception {
    prsrdefX.append("    function Parse"+root.getName()+"(element : IXmlDomElement) : TFhir"+root.getName()+";\r\n");
    srlsdefX.append("    procedure Compose"+root.getName()+"(xml : TMsXmlBuilder; name : string; elem : TFhir"+root.getName()+");\r\n");
    prsrdefJ.append("    function Parse"+root.getName()+" : TFhir"+root.getName()+";\r\n");
    srlsdefJ.append("    procedure Compose"+root.getName()+"(json : TJSONWriter; name : string; elem : TFhir"+root.getName()+");\r\n");
    workingParserX = new StringBuilder();
    workingComposerX = new StringBuilder();
    workingParserJ = new StringBuilder();
    workingComposerJ = new StringBuilder();

   
    StringBuilder def = new StringBuilder();
    StringBuilder defPriv1 = new StringBuilder();
    StringBuilder defPriv2 = new StringBuilder();
    StringBuilder defPub = new StringBuilder();
    StringBuilder impl = new StringBuilder();
    StringBuilder create = new StringBuilder();
    StringBuilder destroy = new StringBuilder();
    StringBuilder assign = new StringBuilder();
    StringBuilder getkids = new StringBuilder();
    StringBuilder getprops = new StringBuilder();
    impl.append("{ "+tn+" }\r\n\r\n");

    
    boolean isRes = superClass.equals("TFhirResource");
    for (ElementDefn e : root.getElements()) {
      generateField(e, defPriv1, defPriv2, defPub, impl, create, destroy, assign, getkids, getprops, tn, "", !isRes, listsAreWrapped, category);
    }

    def.append("  {@Class "+tn+" : "+superClass+"\r\n");
    def.append("    "+Utilities.normaliseEolns(root.getDefinition())+"\r\n");
    def.append("  }\r\n");
    def.append("  {!.Net HL7Connect.Fhir."+tn.substring(5)+"}\r\n");
    def.append("  "+tn+" = class ("+superClass+")\r\n");
    factoryIntf.append("    {@member new"+tn.substring(5)+"\r\n      create a new "+root.getName()+"\r\n    }\r\n    {!script nolink}\r\n    function new"+tn.substring(5)+" : "+tn+";\r\n");    
    factoryImpl.append("function TFhirResourceFactory.new"+tn.substring(5)+" : "+tn+";\r\nbegin\r\n  result := "+tn+".create;\r\nend;\r\n\r\n");
    def.append("  private\r\n");
    def.append(defPriv1.toString());
    def.append(defPriv2.toString());
    def.append("  protected\r\n");
    if (isRes) {
      def.append("    function GetResourceType : TFhirResourceType; override;\r\n");      
    }
    def.append("    Procedure GetChildrenByName(child_name : string; list : TFHIRObjectList); override;\r\n");
    def.append("    Procedure ListProperties(oList : TFHIRPropertyList; bInheritedProperties : Boolean); Override;\r\n");
    def.append("  public\r\n");
    def.append("    constructor Create; Override;\r\n");
    def.append("    destructor Destroy; override;\r\n");
    def.append("    {!script hide}\r\n");
    def.append("    procedure Assign(oSource : TAdvObject); override;\r\n");
    def.append("    function Link : "+tn+"; overload;\r\n");
    def.append("    function Clone : "+tn+"; overload;\r\n");
    def.append("    {!script show}\r\n");
    def.append("  published\r\n");
    def.append(defPub.toString());
    def.append("  end;\r\n");
    def.append("\r\n");
    StringBuilder impl2 = new StringBuilder();
    impl2.append("{ "+tn+" }\r\n\r\n");
    impl2.append("constructor "+tn+".Create;\r\n");
    impl2.append("begin\r\n");
    impl2.append("  inherited;\r\n");
    impl2.append(create.toString());
    impl2.append("end;\r\n\r\n");

    impl2.append("destructor "+tn+".Destroy;\r\n");
    impl2.append("begin\r\n");
    impl2.append(destroy.toString());
    impl2.append("  inherited;\r\n");
    impl2.append("end;\r\n\r\n");
    if (isRes) {
      impl2.append("function "+tn+".GetResourceType : TFhirResourceType;\r\nbegin\r\n  result := frt"+root.getName()+";\r\nend;\r\n\r\n");       
    }
    
    impl2.append("procedure "+tn+".Assign(oSource : TAdvObject);\r\n");
    impl2.append("begin\r\n");
    impl2.append("  inherited;\r\n");
    impl2.append(assign.toString());
    impl2.append("end;\r\n\r\n");
    impl2.append("procedure "+tn+".GetChildrenByName(child_name : string; list : TFHIRObjectList);\r\n");
    impl2.append("begin\r\n");
    impl2.append("  inherited;\r\n");
    impl2.append(getkids.toString());
    impl2.append("end;\r\n\r\n");
    impl2.append("procedure "+tn+".ListProperties(oList: TFHIRPropertyList; bInheritedProperties: Boolean);\r\n");
    impl2.append("begin\r\n");
    impl2.append("  inherited;\r\n");
    impl2.append(getprops.toString());
    impl2.append("end;\r\n\r\n");
    
    impl2.append("function "+tn+".Link : "+tn+";\r\n");
    impl2.append("begin\r\n");
    impl2.append("  result := "+tn+"(inherited Link);\r\n");
    impl2.append("end;\r\n\r\n");
    impl2.append("function "+tn+".Clone : "+tn+";\r\n");
    impl2.append("begin\r\n");
    impl2.append("  result := "+tn+"(inherited Clone);\r\n");
    impl2.append("end;\r\n\r\n");
    getCode(category).classDefs.add(def.toString());
    getCode(category).classImpls.add(impl2.toString() + impl.toString());
    getCode(category).classFwds.add("  "+tn+" = class;\r\n");
    generateParser(tn, isRes, !superClass.equals("TFHIRObject"));
    defineList(tn, tn+"List", category, false);
  }

  private void genResource(ResourceDefn root, String tn, String superClass, boolean listsAreWrapped, ClassCategory category) throws Exception {
    prsrdefX.append("    function Parse"+root.getName()+"(element : IXmlDomElement) : TFhir"+root.getName()+";\r\n");
    srlsdefX.append("    procedure Compose"+root.getName()+"(xml : TMsXmlBuilder; name : string; elem : TFhir"+root.getName()+");\r\n");
    prsrdefJ.append("    function Parse"+root.getName()+" : TFhir"+root.getName()+";\r\n");
    srlsdefJ.append("    procedure Compose"+root.getName()+"(json : TJSONWriter; name : string; elem : TFhir"+root.getName()+");\r\n");
    workingParserX = new StringBuilder();
    workingComposerX = new StringBuilder();
    workingParserJ = new StringBuilder();
    workingComposerJ = new StringBuilder();

    generateSearchEnums(root);
    
    StringBuilder def = new StringBuilder();
    StringBuilder defPriv1 = new StringBuilder();
    StringBuilder defPriv2 = new StringBuilder();
    StringBuilder defPub = new StringBuilder();
    StringBuilder impl = new StringBuilder();
    StringBuilder create = new StringBuilder();
    StringBuilder destroy = new StringBuilder();
    StringBuilder assign = new StringBuilder();
    StringBuilder getkids = new StringBuilder();
    StringBuilder getprops = new StringBuilder();
    impl.append("{ "+tn+" }\r\n\r\n");

    
    boolean isRes = superClass.equals("TFhirResource");
    for (ElementDefn e : root.getRoot().getElements()) {
      if (!isRes || (!e.getName().equals("extension") && !e.getName().equals("text")))
        generateField(e, defPriv1, defPriv2, defPub, impl, create, destroy, assign, getkids, getprops, tn, "", !isRes, listsAreWrapped, ClassCategory.Component);
    }

    def.append("  {@Class "+tn+" : "+superClass+"\r\n");
    def.append("    "+Utilities.normaliseEolns(root.getDefinition())+"\r\n");
    def.append("  }\r\n");
    def.append("  {!.Net HL7Connect.Fhir."+tn.substring(5)+"}\r\n");
    def.append("  "+tn+" = class ("+superClass+")\r\n");
    factoryIntf.append("    {@member new"+tn.substring(5)+"\r\n      create a new "+root.getName()+"\r\n    }\r\n    {!script nolink}\r\n    function new"+tn.substring(5)+" : "+tn+";\r\n");    
    factoryImpl.append("function TFhirResourceFactory.new"+tn.substring(5)+" : "+tn+";\r\nbegin\r\n  result := "+tn+".create;\r\nend;\r\n\r\n");
    def.append("  private\r\n");
    def.append(defPriv1.toString());
    def.append(defPriv2.toString());
    def.append("  protected\r\n");
    def.append("    Procedure GetChildrenByName(child_name : string; list : TFHIRObjectList); override;\r\n");
    def.append("    Procedure ListProperties(oList : TFHIRPropertyList; bInheritedProperties : Boolean); Override;\r\n");
    if (isRes) {
      def.append("    function GetResourceType : TFhirResourceType; override;\r\n");      
    }
    def.append("  public\r\n");
    def.append("    constructor Create; Override;\r\n");
    def.append("    destructor Destroy; override;\r\n");
    def.append("    {!script hide}\r\n");
    def.append("    procedure Assign(oSource : TAdvObject); override;\r\n");
    def.append("    function Link : "+tn+"; overload;\r\n");
    def.append("    function Clone : "+tn+"; overload;\r\n");
    def.append("    {!script show}\r\n");
    def.append("  published\r\n");
    def.append(defPub.toString());
    def.append("  end;\r\n");
    def.append("\r\n");
    StringBuilder impl2 = new StringBuilder();
    impl2.append("{ "+tn+" }\r\n\r\n");
    impl2.append("constructor "+tn+".Create;\r\n");
    impl2.append("begin\r\n");
    impl2.append("  inherited;\r\n");
    impl2.append(create.toString());
    impl2.append("end;\r\n\r\n");

    impl2.append("destructor "+tn+".Destroy;\r\n");
    impl2.append("begin\r\n");
    impl2.append(destroy.toString());
    impl2.append("  inherited;\r\n");
    impl2.append("end;\r\n\r\n");
    if (isRes) {
      impl2.append("function "+tn+".GetResourceType : TFhirResourceType;\r\nbegin\r\n  result := frt"+root.getName()+";\r\nend;\r\n\r\n");       
    }
    
    impl2.append("procedure "+tn+".Assign(oSource : TAdvObject);\r\n");
    impl2.append("begin\r\n");
    impl2.append("  inherited;\r\n");
    impl2.append(assign.toString());
    impl2.append("end;\r\n\r\n");
    impl2.append("procedure "+tn+".GetChildrenByName(child_name : string; list : TFHIRObjectList);\r\n");
    impl2.append("begin\r\n");
    impl2.append("  inherited;\r\n");
    impl2.append(getkids.toString());
    impl2.append("end;\r\n\r\n");
    impl2.append("procedure "+tn+".ListProperties(oList: TFHIRPropertyList; bInheritedProperties: Boolean);\r\n");
    impl2.append("begin\r\n");
    impl2.append("  inherited;\r\n");
    impl2.append(getprops.toString());
    impl2.append("end;\r\n\r\n");
    
    impl2.append("function "+tn+".Link : "+tn+";\r\n");
    impl2.append("begin\r\n");
    impl2.append("  result := "+tn+"(inherited Link);\r\n");
    impl2.append("end;\r\n\r\n");
    impl2.append("function "+tn+".Clone : "+tn+";\r\n");
    impl2.append("begin\r\n");
    impl2.append("  result := "+tn+"(inherited Clone);\r\n");
    impl2.append("end;\r\n\r\n");
    getCode(category).classDefs.add(def.toString());
    getCode(category).classImpls.add(impl2.toString() + impl.toString());
    getCode(category).classFwds.add("  "+tn+" = class;\r\n");
    generateParser(tn, isRes, !superClass.equals("TFHIRObject"));
  }

  private void generateSearchEnums(ResourceDefn r) throws Exception {
    StringBuilder def = new StringBuilder();
    StringBuilder con = new StringBuilder();
    StringBuilder con2 = new StringBuilder();
    StringBuilder con3 = new StringBuilder();
    StringBuilder con4 = new StringBuilder();
    StringBuilder con5 = new StringBuilder();
    StringBuilder con6 = new StringBuilder();
    StringBuilder con7 = new StringBuilder();

    String tn = "TSearchParams"+r.getName();
    String prefix = "sp"+r.getName()+"_";

    if (!enumsDone.contains(prefix)) {
      enumsDone.add(prefix);
      def.append("  {@Enum "+tn+"\r\n");
      def.append("    Search Parameters for "+r.getName()+"\r\n");
      def.append("  }\r\n");
      def.append("  "+tn+" = (\r\n");

      con3.append("  CODES_"+tn+" : Array["+tn+"] of String = (");
      con4.append("  TYPES_"+tn+" : Array["+tn+"] of TFhirSearchParamType = (");
      con5.append("  REPEATS_"+tn+" : Array["+tn+"] of TFhirSearchRepeatBehavior = (");
      con.append("  DESC_"+tn+" : Array["+tn+"] of String = (");
      con2.append("//  CHECK_"+tn+" : Array["+tn+"] of "+tn+" = (");
      con6.append("  PATHS_"+tn+" : Array["+tn+"] of String = (");
      con7.append("  TARGETS_"+tn+" : Array["+tn+"] of TFhirResourceTypeSet = (");

      int l = r.getSearchParams().size();
      int i = 0;

      List<String> names = new ArrayList<String>();
      names.addAll(r.getSearchParams().keySet());
      Collections.sort(names);
      for (String name : names) {
    	SearchParameter p = r.getSearchParams().get(name);
        i++;
        String n = p.getCode().replace("$", "_");
        String d = Utilities.normaliseEolns(p.getDescription());
        String nf = n.replace("-", "_");
        String t = getTarget(p.getPath(), r);
        if (i == l) {
          def.append("    "+prefix+getTitle(nf)+"); {@enum.value "+prefix+getTitle(nf)+" "+d+" }\r\n");
          con.append("'"+defCodeType.escape(d)+"');");
          con2.append(" "+prefix+getTitle(nf)+");");
          con4.append(" SearchParamType"+getTitle(p.getType().toString())+");");
          con3.append("'"+defCodeType.escape(n)+"');");
          con5.append(" SearchRepeatBehavior"+getTitle(p.getRepeatMode().toString())+");");
          con6.append("'"+defCodeType.escape(n+": "+p.getPath())+"');");
          con7.append(""+t+");");
        }
        else {
          def.append("    "+prefix+getTitle(nf)+", {@enum.value "+prefix+getTitle(nf)+" "+d+" }\r\n");
          con.append("'"+defCodeType.escape(d)+"', ");
          con2.append(" "+prefix+getTitle(nf)+", ");
          con4.append(" SearchParamType"+getTitle(p.getType().toString())+", ");
          con3.append("'"+defCodeType.escape(n)+"', ");
          con5.append(" SearchRepeatBehavior"+getTitle(p.getRepeatMode().toString())+", ");
          con6.append("'"+defCodeType.escape(n+": "+p.getPath())+"',\r\n     ");
          con7.append(""+t+", ");
        }
      }

      defCodeRes.enumDefs.add(def.toString());
      defCodeRes.enumConsts.add(con3.toString());
      defCodeRes.enumConsts.add(con5.toString());
      defCodeRes.enumConsts.add(con4.toString());
      defCodeRes.enumConsts.add(con.toString());
      defCodeRes.enumConsts.add(con2.toString());
      defCodeRes.enumConsts.add(con6.toString());
      defCodeRes.enumConsts.add(con7.toString());
    }
  }
  
  private String getTarget(String path, ResourceDefn r) throws Exception {
    if (Utilities.noString(path))
	  return "[]";
    try {
      ElementDefn e = r.getRoot().getElementForPath(path, definitions, "generating pascal code");
      if (!e.typeCode().startsWith("Resource("))
    	return "[]";
      else if (e.typeCode().endsWith("(Any)"))
        return "ALL_RESOURCE_TYPES";
      else {
    	  StringBuilder s = new StringBuilder();
    	  s.append("[");
    	  boolean first = true;
    	  for (String p : e.getTypes().get(0).getParams()) {
    		  if (definitions.hasResource(p)) {
    			  if (!first)
    				  s.append(", ");
    			  s.append("frt"+p);
    			  first = false;
    		  }
    	  }
    	  s.append("]");
    	  return s.toString();
      }
      
    } catch (Exception e) {
    	return "[]";
    }
    
	  
}

private void generateEnum(ElementDefn e) throws Exception {
    String tn = typeNames.get(e);
    BindingSpecification cd = getConceptDomain(e.getBindingName());
    
    
//    StringBuilder pfx = new StringBuilder();
//    for (char c : tn.toCharArray()) {
//      if (Character.isUpperCase(c))
//        pfx.append(c);
//    }
//    String prefix = pfx.toString().toLowerCase();
    String prefix = tn.substring(5);
    if (!enumsDone.contains(prefix)) {
      enumsDone.add(prefix);
      StringBuilder def = new StringBuilder();
      StringBuilder con = new StringBuilder();
      def.append("  {@Enum "+tn+"\r\n");
      def.append("    "+Utilities.normaliseEolns(cd.getDefinition())+"\r\n");
      def.append("  }\r\n");
      def.append("  "+tn+" = (\r\n");
      con.append("  CODES_"+tn+" : Array["+tn+"] of String = (");

      int l = cd.getCodes().size();
      int i = 0;
      def.append("    "+prefix+"Null,  {@enum.value "+prefix+"Null Value is missing from Instance }\r\n");
      con.append("'', ");
      for (DefinedCode c : cd.getCodes()) {
        i++;
        String cc = c.getCode();
        if (cc.equals("-"))
        	cc = "Minus";
        else if (cc.equals("+"))
        	cc = "Plus";
        else {
        	cc = cc.replace("-", " ").replace("+", " ");
        	cc = Utilities.camelCase(cc);
        	cc = cc.replace(">=", "greaterOrEquals").replace("<=", "lessOrEquals").replace("<", "lessThan").replace(">", "greaterThan").replace("=", "equal");
        }

        cc = prefix + getTitle(cc);
        if (GeneratorUtils.isDelphiReservedWord(cc))
          cc = cc + "_";
        if (i == l) {
          def.append("    "+cc+"); {@enum.value "+cc+" "+Utilities.normaliseEolns(c.getDefinition())+" }\r\n");
          con.append("'"+c.getCode()+"');");
        }
        else {
          def.append("    "+cc+", {@enum.value "+cc+" "+Utilities.normaliseEolns(c.getDefinition())+" }\r\n");
          con.append("'"+c.getCode()+"', ");
        }
      }
      def.append("  "+tn+"List = set of "+tn+";\r\n");
      defCodeType.enumDefs.add(def.toString());
      defCodeType.enumConsts.add(con.toString());
      defCodeType.enumProcs.add("Function "+tn+"ListAsInteger(aSet : "+tn+"List) : Integer; overload;");
      defCodeType.enumProcs.add("Function IntegerAs"+tn+"List(i : integer) : "+tn+"List; overload;");
      
      
      StringBuilder impl = new StringBuilder();
      
      impl.append("function "+tn+"ListAsInteger(aSet : "+tn+"List) : Integer;\r\n");
      impl.append("var\r\n");
      impl.append("  a : "+tn+";\r\n");
      impl.append("begin\r\n");
      impl.append("  result := 0;\r\n");
      impl.append("  for a := low("+tn+") to high("+tn+") do\r\n");
      impl.append("  begin\r\n");
      impl.append("    assert(ord(a) < 32);\r\n");
      impl.append("    if a in aSet then\r\n");
      impl.append("      result := result + 1 shl (ord(a));\r\n");
      impl.append("  end;\r\n");
      impl.append("end;\r\n\r\n");

      impl.append("function IntegerAs"+tn+"List(i : Integer) : "+tn+"List;\r\n");
      impl.append("var\r\n");
      impl.append("  aLoop : "+tn+";\r\n");
      impl.append("begin\r\n");
      impl.append("  result := [];\r\n");
      impl.append("  for aLoop := low("+tn+") to high("+tn+") Do\r\n");
      impl.append("  begin\r\n");
      impl.append("    assert(ord(aLoop) < 32);\r\n");
      impl.append("    if i and (1 shl (ord(aLoop))) > 0 Then\r\n");
      impl.append("      result := result + [aLoop];\r\n");
      impl.append("  end;\r\n");
      impl.append(" end;\r\n\r\n");
      
      defCodeType.classImpls.add(impl.toString());
    }
  }

  private void generateType(ElementDefn e, boolean listsAreWrapped, ClassCategory category) throws Exception {
    String tn = typeNames.get(e);

    prsrdefX.append("    function Parse"+tn.substring(5)+"(element : IXmlDomElement) : "+tn+";\r\n");
    srlsdefX.append("    procedure Compose"+tn.substring(5)+"(xml : TMsXmlBuilder; name : string; elem : "+tn+");\r\n");
    prsrdefJ.append("    function Parse"+tn.substring(5)+" : "+tn+";\r\n");
    srlsdefJ.append("    procedure Compose"+tn.substring(5)+"(json : TJSONWriter; name : string; elem : "+tn+");\r\n");
    workingParserX = new StringBuilder();
    workingComposerX = new StringBuilder();
    workingParserJ = new StringBuilder();
    workingComposerJ = new StringBuilder();
    
    StringBuilder def = new StringBuilder();
    StringBuilder defPriv1 = new StringBuilder();
    StringBuilder defPriv2 = new StringBuilder();
    StringBuilder defPub = new StringBuilder();
    StringBuilder impl = new StringBuilder();
    StringBuilder create = new StringBuilder();
    StringBuilder destroy = new StringBuilder();
    StringBuilder assign = new StringBuilder();
    StringBuilder getkids = new StringBuilder();
    StringBuilder getprops = new StringBuilder();
    
    def.append("  {@Class "+tn+" : TFhirElement\r\n");
    def.append("    "+Utilities.normaliseEolns(e.getDefinition())+"\r\n");
    def.append("  }\r\n");
    def.append("  {!.Net HL7Connect.Fhir."+tn.substring(5)+"}\r\n");
    def.append("  "+tn+" = class (TFhirElement)\r\n");
    factoryIntf.append("    {@member new"+tn.substring(5)+"\r\n      create a new "+e.getName()+"\r\n    }\r\n    {!script nolink}\r\n    function new"+tn.substring(5)+" : "+tn+";\r\n");    
    factoryImpl.append("function TFhirResourceFactory.new"+tn.substring(5)+" : "+tn+";\r\nbegin\r\n  result := "+tn+".create;\r\nend;\r\n\r\n");
    impl.append("{ "+tn+" }\r\n\r\n");
    
//    if (hasLists(e)) {
//      s.append("      public "+tn+"()\r\n");
//      s.append("      {\r\n");
//      for (ElementDefn c : e.getElements()) {
//        if (c.unbounded()) {
//          s.append("        "+getElementName(c.getName())+" = new List<"+typeNames.get(c)+">();\r\n");         
//        }
//      }
//      s.append("      }\r\n");
//      s.append("\r\n");
//      
//    }
    for (ElementDefn c : e.getElements()) {
      generateField(c, defPriv1, defPriv2, defPub, impl, create, destroy, assign, getkids, getprops, tn, "", false, listsAreWrapped, category);
    }

    def.append("  private\r\n");
    def.append(defPriv1.toString());
    def.append(defPriv2.toString());
    def.append("  protected\r\n");
    def.append("    Procedure GetChildrenByName(child_name : string; list : TFHIRObjectList); override;\r\n");
    def.append("    Procedure ListProperties(oList : TFHIRPropertyList; bInheritedProperties : Boolean); Override;\r\n");
    def.append("  public\r\n");
    def.append("    constructor Create; Override;\r\n");
    def.append("    destructor Destroy; override;\r\n");
    def.append("    {!script hide}\r\n");
    def.append("    procedure Assign(oSource : TAdvObject); override;\r\n");
    def.append("    function Link : "+tn+"; overload;\r\n");
    def.append("    function Clone : "+tn+"; overload;\r\n");
    def.append("    {!script show}\r\n");
    def.append("  published\r\n");
    def.append(defPub.toString());
    def.append("  end;\r\n");
    def.append("\r\n");
    StringBuilder impl2 = new StringBuilder();
    impl2.append("{ "+tn+" }\r\n\r\n");
    impl2.append("constructor "+tn+".Create;\r\n");
    impl2.append("begin\r\n");
    impl2.append("  inherited;\r\n");
    impl2.append(create.toString());
    impl2.append("end;\r\n\r\n");

    impl2.append("destructor "+tn+".Destroy;\r\n");
    impl2.append("begin\r\n");
    impl2.append(destroy.toString());
    impl2.append("  inherited;\r\n");
    impl2.append("end;\r\n\r\n");
    
    impl2.append("procedure "+tn+".Assign(oSource : TAdvObject);\r\n");
    impl2.append("begin\r\n");
    impl2.append("  inherited;\r\n");
    impl2.append(assign.toString());
    impl2.append("end;\r\n\r\n");
    impl2.append("procedure "+tn+".GetChildrenByName(child_name : string; list : TFHIRObjectList);\r\n");
    impl2.append("begin\r\n");
    impl2.append("  inherited;\r\n");
    impl2.append(getkids.toString());
    impl2.append("end;\r\n\r\n");
    impl2.append("procedure "+tn+".ListProperties(oList: TFHIRPropertyList; bInheritedProperties: Boolean);\r\n");
    impl2.append("begin\r\n");
    impl2.append("  inherited;\r\n");
    impl2.append(getprops.toString());
    impl2.append("end;\r\n\r\n");
    
    
    impl2.append("function "+tn+".Link : "+tn+";\r\n");
    impl2.append("begin\r\n");
    impl2.append("  result := "+tn+"(inherited Link);\r\n");
    impl2.append("end;\r\n\r\n");
    impl2.append("function "+tn+".Clone : "+tn+";\r\n");
    impl2.append("begin\r\n");
    impl2.append("  result := "+tn+"(inherited Clone);\r\n");
    impl2.append("end;\r\n\r\n");
    
    getCode(category).classDefs.add(def.toString());
    getCode(category).classImpls.add(impl2.toString() + impl.toString());
    getCode(category).classFwds.add("  "+tn+" = class;\r\n");
    generateParser(tn, false, true);
    defineList(tn, tn+"List", category, false);
  }

  private void generateParser(String tn, boolean isResource, boolean isElement) {
    String s = workingParserX.toString();
    prsrImpl.append(
            "function TFHIRXmlParser.Parse"+tn.substring(5)+"(element : IXmlDomElement) : "+tn+";\r\n"+
            "var\r\n"+
            "  child : IXMLDOMElement;\r\n");
    
    prsrImpl.append(s.contains("item") ? "  item : IXMLDOMElement;\r\n" : "");
    prsrImpl.append(
            "begin\r\n"+
            "  result := "+tn+".create;\r\n"+
            "  try\r\n");
    if (isElement)
      if (isResource)
        prsrImpl.append("    parseResourceAttributes(result, element);\r\n");
      else
        prsrImpl.append("    parseElementAttributes(result, element);\r\n");

    prsrImpl.append(
            "    child := FirstChild(element);\r\n"+
            "    while (child <> nil) do\r\n"+
            "    begin\r\n");
//    if (isResource)
//      prsrImpl.append(
//          "      if (child.baseName = 'text') then\r\n"+
//          "        result.text := ParseNarrative(child)\r\n"+
//          "      else if (child.baseName = 'extension') then\r\n"+
//          "        result.extensionList.add(ParseExtension(child))\r\n"+
//            s);
//    else 
      prsrImpl.append("      "+s.substring(11));
    if (!isElement)
      prsrImpl.append(
          "      else\r\n");
    else if (isResource)
      prsrImpl.append(
          "      else if Not ParseResourceChild(result, child) then\r\n");
    else
      prsrImpl.append(
      "      else if Not ParseElementChild(result, child) then\r\n");
    prsrImpl.append(
            "         UnknownContent(child);\r\n"+
            "      child := NextSibling(child);\r\n"+
            "    end;\r\n");
    if (isElement)
      prsrImpl.append(
            "    closeOutElement(result, element);\r\n");
    prsrImpl.append(
            "\r\n"+
            "    result.link;\r\n"+
            "  finally\r\n"+
            "    result.free;\r\n"+
            "  end;\r\n"+
            "end;\r\n\r\n"
        );

    s = workingComposerX.toString();
    prsrImpl.append(
            "procedure TFHIRXmlComposer.Compose"+tn.substring(5)+"(xml : TMsXmlBuilder; name : string; elem : "+tn+");\r\n");
    if (s.contains("for i := "))
      prsrImpl.append("var\r\n  i : integer;\r\n");
    prsrImpl.append(
            "begin\r\n"+
            "  if (elem = nil) then\r\n    exit;\r\n");
    if (isElement)
      if (isResource)
        prsrImpl.append("  composeResourceAttributes(xml, elem);\r\n");
      else
        prsrImpl.append("  composeElementAttributes(xml, elem);\r\n");
    prsrImpl.append(
            "  xml.open(name);\r\n");
    if (isElement)
      if (isResource)
        prsrImpl.append("  composeResourceChildren(xml, elem);\r\n");
      else
        prsrImpl.append("  composeElementChildren(xml, elem);\r\n");
    
    prsrImpl.append(s);
    if (isElement)
      prsrImpl.append("  closeOutElement(xml, elem);\r\n");
    prsrImpl.append(
            "  xml.close(name);\r\n"+
            "end;\r\n\r\n"
        );

    s = workingParserJ.toString();
    prsrImpl.append(
            "function TFHIRJsonParser.Parse"+tn.substring(5)+" : "+tn+";\r\n"+
            "begin\r\n"+
            "  json.next;\r\n"+    
            "  result := "+tn+".create;\r\n"+
            "  try\r\n"+
            "    while (json.ItemType <> jpitEnd) do\r\n"+
            "    begin\r\n      ");
    prsrImpl.append(s.substring(11));
    if (!isElement)
      prsrImpl.append(
          "      else\r\n");
    else if (isResource)
      prsrImpl.append(
          "      else if not ParseResourceProperty(result) then\r\n");
    else
      prsrImpl.append(
        "      else if not ParseElementProperty(result) then\r\n");
    prsrImpl.append(
            "         UnknownContent;\r\n"+
            "      json.next;\r\n"+    
            "    end;\r\n"+
            "\r\n"+
            "    result.link;\r\n"+
            "  finally\r\n"+
            "    result.free;\r\n"+
            "  end;\r\n"+
            "end;\r\n\r\n"
        );

    s = workingComposerJ.toString();
    prsrImpl.append(
            "procedure TFHIRJsonComposer.Compose"+tn.substring(5)+"(json : TJSONWriter; name : string; elem : "+tn+");\r\n");
    if (s.contains("for i := ") || isResource)
      prsrImpl.append("var\r\n  i : integer;\r\n");
    prsrImpl.append(
            "begin\r\n"+
            "  if (elem = nil) then\r\n    exit;\r\n"+
            "  json.valueObject(name);\r\n");
    if (isElement)
      if (isResource)
        prsrImpl.append("  ComposeResourceProperties(json, elem);\r\n");
      else
        prsrImpl.append("  ComposeElementProperties(json, elem);\r\n");
    prsrImpl.append(s);
    prsrImpl.append(
            "  json.finishObject;\r\n"+
            "end;\r\n\r\n"
        );

  }

//  private boolean hasLists(ElementDefn e) {
//    for (ElementDefn c : e.getElements()) {
//      if (c.unbounded())
//        return true;
//    }
//    return false;
//  }

  private void scanNestedTypes(ElementDefn root, String path, ElementDefn e) throws Exception {
    String tn = null;
    if (e.typeCode().equals("code") && e.hasBinding()) {
      BindingSpecification cd = getConceptDomain(e.getBindingName());
      if (cd != null && cd.getBinding() == BindingSpecification.Binding.CodeList) {
        tn = "TFhir"+getTitle(getCodeList(cd.getReference()).substring(1));
        if (!enumNames.contains(tn)) {
          enumNames.add(tn);
          enums.add(e);
        }
        typeNames.put(e,  tn);
      }
    }
    if (tn == null) {
      if (e.usesCompositeType()) {
        tn = typeNames.get(getElementForPath(root, e.typeCode().substring(1)));
        typeNames.put(e,  tn);
      } else if (e.getTypes().size() > 0) {
        boolean hasId = root.typeCode().equals("Resource") || e.unbounded();
        tn = getTypeName(e, hasId);
        typeNames.put(e,  tn);
      } else 
      {
        tn = "TFhir"+path+getTitle(e.getName());
        strucs.add(e);
        typeNames.put(e,  tn);
        for (ElementDefn c : e.getElements()) {
          scanNestedTypes(root, path+getTitle(e.getName()), c);
        }
      }
    }
  }

  private Object getElementForPath(ElementDefn root, String pathname) throws Exception {
    String[] path = pathname.split("\\.");
    if (!path[0].equals(root.getName()))
      throw new Exception("Element Path '"+pathname+"' is not legal in this context");
    ElementDefn res = root;
    for (int i = 1; i < path.length; i++)
    {
      String en = path[i];
      if (en.length() == 0)
        throw new Exception("Improper path "+pathname);
      ElementDefn t = res.getElementByName(en);
      if (t == null) {
        throw new Exception("unable to resolve "+pathname);
      }
      res = t; 
    }
    return res;

  }

  private String getCodeList(String binding) {
    StringBuilder b = new StringBuilder();
    boolean up = true;
    for (char ch: binding.toCharArray()) {
      if (ch == '-')
        up = true;
      else if (up) {
        b.append(Character.toUpperCase(ch));
        up = false;
      }
      else        
        b.append(ch);
    }
    return b.toString();
  }

  private BindingSpecification getConceptDomain(String conceptDomain) {
    for (BindingSpecification cd : definitions.getBindings().values())
      if (cd.getName().equals(conceptDomain))
        return cd;
    return null;
  }

  private void generateField(ElementDefn e, StringBuilder defPriv1, StringBuilder defPriv2, StringBuilder defPub, StringBuilder impl, StringBuilder create, StringBuilder destroy, StringBuilder assign, StringBuilder getkids, StringBuilder getprops, String cn, String pt, Boolean isType, boolean listsAreWrapped, ClassCategory category) throws Exception {
    String tn;
    if (e.getTypes().size() > 0 && e.getTypes().get(0).isUnboundGenericParam())
      tn = pt;
    else
      tn = typeNames.get(e);
    if (tn == null) {
      if (e.getName().equals("extension"))
        tn = "TFhirExtension";
      else
        tn = getTypeName(e, isType || e.unbounded());
    }
    

    String parse = null;
    String ttb = "";
    String tta = "";
    String propV = "F"+getTitle(getElementName(e.getName()));
    if (typeIsSimple(tn)) {
      if (enumNames.contains(tn)) {        
        parse = "ParseEnum(CODES_"+tn+", child)";
        ttb = "CODES_"+tn+"[";
        tta = "]";
//        propV = "CODES_"+tn+"["+propV+ "]";
      } else if (tn.equals("Integer")) {
        parse = "StringToInteger32(child.text)";
        ttb = "inttostr(";
        tta = ")";
        propV = "inttostr("+propV+ ")";
      } else if (tn.equals("Boolean")) {
        parse = "StringToBoolean(child.text)";
        propV = "booleanToString("+propV+ ")";
      } else if (tn.equals("TDateAndTime")) {
        parse = "TDateAndTime.createXml(child.text)";
        propV = propV+".AsXML";
      } else if (tn.equals("TFhirXHtmlNode"))
        parse = "ParseXhtml(child)";
//      else if (tn.equals("TXmlIdReference"))
//        parse = "GetAttribute(child, 'idref')";
      else
        parse = "child.text";
    } else if (tn.equals("TSmartDecimal")) 
      propV = propV+".asString";
    else 
      propV = propV+".Link";
    
    String parseJ = null;
    if (typeIsSimple(tn)) {
      if (enumNames.contains(tn))
        parseJ = "ParseEnum(CODES_"+tn+")";
      else if (tn.equals("Integer"))
        parseJ = "StringToInteger32(json.itemValue)";
      else if (tn.equals("Boolean"))
        parseJ = "StringToBoolean(json.itemValue)";
      else if (tn.equals("TDateAndTime"))
        parseJ = "TDateAndTime.createXml(json.itemValue)";
      else if (tn.equals("TFhirXHtmlNode"))
        parseJ = "ParseXhtml()";
      else
        parseJ = "json.itemValue";
    }
    String srlsd = "Text";
    String srlsdJ = "Prop";
    String srls = "#";
    if (typeIsSimple(tn)) {
      if (enumNames.contains(tn)) {
        srls = "CODES_"+tn+"[#]";
      } else if (tn.equals("Integer")) {
    	  srls = "IntegerToString(#)";
      } else if (tn.equals("Boolean")) {
    	  srls = "BooleanToString(#)";
      } else if (tn.equals("TDateAndTime")) {
    	  srls = "#.AsXml";
      };
    }


    String s = getElementName(e.getName()); 
    if (e.unbounded()) {
    	if (enumNames.contains(tn)) {         
    		defPriv1.append("    F"+getTitle(s)+" : TFhirEnumList;\r\n"); 
    		defPriv2.append("    Function Get"+getTitle(s)+"ST : "+tn+"List;\r\n");
    		defPriv2.append("    Procedure Set"+getTitle(s)+"ST(value : "+tn+"List);\r\n");
    		defPub.append("    {@member "+s+"\r\n");
    		defPub.append("      "+Utilities.normaliseEolns(e.getDefinition())+"\r\n");
    		defPub.append("    }\r\n");
    		defPub.append("    property "+s+" : TFhirEnumList read F"+getTitle(s)+";\r\n");
    		defPub.append("    {@member "+s+"ST\r\n");
    		defPub.append("      Typed access to "+Utilities.normaliseEolns(e.getDefinition())+"\r\n");
    		defPub.append("    }\r\n");
    		defPub.append("    property "+s+"ST : "+tn+"List read Get"+getTitle(s)+"ST write Set"+getTitle(s)+"ST;\r\n");
    		create.append("  F"+getTitle(s)+" := TFHIREnumList.Create;\r\n");
    		destroy.append("  F"+getTitle(s)+".Free;\r\n");
    		assign.append("  F"+getTitle(s)+".Assign("+cn+"(oSource).F"+getTitle(s)+");\r\n");
    		getkids.append("  if (child_name = '"+getElementName(e.getName())+"') Then\r\n     list.addAll(F"+getTitle(s)+");\r\n");
    		getprops.append("  oList.add(TFHIRProperty.create(self, '"+e.getName()+"', '"+e.typeCode()+"', F"+getTitle(s)+".Link)){3};\r\n");
            impl.append("Function "+cn+".Get"+getTitle(s)+"ST : "+tn+"List;\r\n  var i : integer;\r\nbegin\r\n  result := [];\r\n  for i := 0 to "+s+".count - 1 do\r\n    result := result + ["+tn+"(StringArrayIndexOf(CODES_"+tn+", "+s+"[i].value))];\r\nend;\r\n\r\n");
            impl.append("Procedure "+cn+".Set"+getTitle(s)+"ST(value : "+tn+"List);\r\nvar a : "+tn+";\r\nbegin\r\n  "+s+".clear;\r\n  for a := low("+tn+") to high("+tn+") do\r\n    if a in value then\r\n      "+s+".add(TFhirEnum.create(CODES_"+tn+"[a]));\r\nend;\r\n\r\n");
    	} else {
    		String tnl;
    		if (tn.contains("{"))
    			tnl = tn.substring(0, tn.indexOf('{'))+"List"+tn.substring(tn.indexOf('{'));
    		else
    			tnl = tn+"List";
    		s = s+"List";
    		defPriv1.append("    F"+s+" : "+tnl+";\r\n");
    		defPub.append("    {@member "+s+"\r\n");
    		defPub.append("      "+Utilities.normaliseEolns(e.getDefinition())+"\r\n");
    		defPub.append("    }\r\n");
    		defPub.append("    property "+s+" : "+tnl+" read F"+getTitle(s)+";\r\n");
    		defPub.append("\r\n");
    		create.append("  F"+getTitle(s)+" := "+tnl+".Create;\r\n");
    		destroy.append("  F"+getTitle(s)+".Free;\r\n");
    		assign.append("  F"+getTitle(s)+".Assign("+cn+"(oSource).F"+getTitle(s)+");\r\n");
    		getkids.append("  if (child_name = '"+getElementName(e.getName())+"') Then\r\n     list.addAll(F"+getTitle(s)+");\r\n");
    		getprops.append("  oList.add(TFHIRProperty.create(self, '"+e.getName()+"', '"+e.typeCode()+"', F"+getTitle(s)+".Link)){3};\r\n");

    		//      defineList(tn, tnl, category);
    		if (!typeIsSimple(tn)) {
    			if (!e.getName().equals("[type]") && !e.getName().contains("[x]")) {
    				parse = "Parse"+parseName(tn)+"(child)";
    				parseJ = "Parse"+parseName(tn)+"";
    				srlsd = "Compose"+parseName(tn);
    				srlsdJ = "Compose"+parseName(tn);
    			} else {
    				throw new Exception("not supported at "+e.getName()+" - complex type "+tn);
    			}
    		};
    		workingParserX.append("      else if (child.baseName = '"+e.getName()+"') then\r\n"+
    				"        result."+s+".Add("+parse+")\r\n");
    		workingComposerX.append("  for i := 0 to elem."+s+".Count - 1 do\r\n"+
    				"    "+srlsd+"(xml, '"+e.getName()+"', "+srls.replace("#", "elem."+s+"[i]")+");\r\n");
    		workingParserJ.append("      else if (json.ItemName = '"+e.getName()+"') then\r\n"+
    				"      begin\r\n"+
    				"        json.checkState(jpitArray);\r\n"+
    				"        json.Next;\r\n"+
    				"        while (json.ItemType <> jpitEnd) do\r\n"+
    				"        begin\r\n"+
    				"          result."+s+".Add("+parseJ+");\r\n"+
    				"          json.Next;\r\n"+
    				"        end;\r\n"+
    				"      end\r\n");

    		workingComposerJ.append("  if elem."+s+".Count > 0 then\r\n"+
    				"  begin\r\n"+
    				"    json.valueArray('"+e.getName()+"');\r\n"+
    				"    for i := 0 to elem."+s+".Count - 1 do\r\n"+
    				"      "+srlsdJ+"(json, '',"+srls.replace("#", "elem."+s+"[i]")+");\r\n"+
    				"    json.FinishArray;\r\n"+
    				"  end;\r\n");
    	}
    } else {
      if (enumNames.contains(tn)) {         
        defPriv1.append("    F"+getTitle(s)+" : TFhirEnum;\r\n"); 
        defPriv2.append("    Procedure Set"+getTitle(s)+"(value : TFhirEnum);\r\n");
        defPriv2.append("    Function Get"+getTitle(s)+"ST : "+tn+";\r\n");
        defPriv2.append("    Procedure Set"+getTitle(s)+"ST(value : "+tn+");\r\n");
        defPub.append("    {@member "+s+"\r\n");
        defPub.append("      "+Utilities.normaliseEolns(e.getDefinition())+"\r\n");
        defPub.append("    }\r\n");
        defPub.append("    property "+s+" : TFhirEnum read F"+getTitle(s)+" write Set"+getTitle(s)+";\r\n");
        defPub.append("    {@member "+s+"ST\r\n");
        defPub.append("      Typed access to "+Utilities.normaliseEolns(e.getDefinition())+"\r\n");
        defPub.append("    }\r\n");
        defPub.append("    property "+s+"ST : "+tn+" read Get"+getTitle(s)+"ST write Set"+getTitle(s)+"ST;\r\n");
      } else {
        
        defPriv1.append("    F"+getTitle(s)+" : "+tn+";\r\n");
        defPriv2.append("    Procedure Set"+getTitle(s)+"(value : "+tn+");\r\n");
        defPub.append("    {@member "+s+"\r\n");
        defPub.append("      "+Utilities.normaliseEolns(e.getDefinition())+"\r\n");
        defPub.append("    }\r\n");
        defPub.append("    property "+s+" : "+tn+" read F"+getTitle(s)+" write Set"+getTitle(s)+";\r\n");
        if (simpleTypes.containsKey(tn)) {
          String sn = simpleTypes.get(tn);
          defPriv2.append("    Function Get"+getTitle(s)+"ST : "+sn+";\r\n");
          defPriv2.append("    Procedure Set"+getTitle(s)+"ST(value : "+sn+");\r\n");
          defPub.append("    {@member "+s+"ST\r\n");
          defPub.append("      Typed access to "+Utilities.normaliseEolns(e.getDefinition())+"\r\n");
          defPub.append("    }\r\n");
          defPub.append("    property "+s+"ST : "+sn+" read Get"+getTitle(s)+"ST write Set"+getTitle(s)+"ST;\r\n");
        }
      }
      defPub.append("\r\n");
      if (typeIsSimple(tn) && !tn.equals("TFhirXHtmlNode")) {
        if (enumNames.contains(tn)) {         
          impl.append("Procedure "+cn+".Set"+getTitle(s)+"(value : TFhirEnum);\r\nbegin\r\n  F"+getTitle(s)+".free;\r\n  F"+getTitle(s)+" := value;\r\nend;\r\n\r\n");
          impl.append("Function "+cn+".Get"+getTitle(s)+"ST : "+tn+";\r\nbegin\r\n  if F"+getTitle(s)+" = nil then\r\n    result := "+tn+"(0)\r\n  else\r\n    result := "+tn+"(StringArrayIndexOf(CODES_"+tn+", "+getTitle(s)+".value));\r\nend;\r\n\r\n");
          impl.append("Procedure "+cn+".Set"+getTitle(s)+"ST(value : "+tn+");\r\nbegin\r\n  if ord(value) = 0 then\r\n    "+getTitle(s)+" := nil\r\n  else\r\n    "+getTitle(s)+" := TFhirEnum.create(CODES_"+tn+"[value]);\r\nend;\r\n\r\n");
        } else {
          impl.append("Procedure "+cn+".Set"+getTitle(s)+"(value : TFhirEnum);\r\nbegin\r\n  F"+getTitle(s)+".free;\r\n  F"+getTitle(s)+" := value;\r\nend;\r\n\r\n");
        }
        assign.append("  F"+getTitle(s)+" := "+cn+"(oSource).F"+getTitle(s)+".Link;\r\n");
        getkids.append("  if (child_name = '"+getElementName(e.getName())+"') Then\r\n     list.add(F"+getTitle(s)+".Link);\r\n");
        getprops.append("  oList.add(TFHIRProperty.create(self, '"+e.getName()+"', '"+e.typeCode()+"', "+propV+".Link));{1}\r\n");
        workingParserX.append("      else if (child.baseName = '"+e.getName()+"') then\r\n        result."+s+" := "+parse+"\r\n");
        workingParserJ.append("      else if (json.ItemName = '"+e.getName()+"') then\r\n        result."+s+" := "+parseJ+"\r\n");
//        if (tn.equals("TXmlIdReference")) {
//          workingComposerX.append("  if (elem."+e.getName()+" <> '') then\r\n");
//          workingComposerX.append("  begin\r\n");
//          workingComposerX.append("    attribute(xml, 'idref', elem."+e.getName()+");\r\n");
//          workingComposerX.append("    xml.Tag('"+e.getName()+"');\r\n");
//          workingComposerX.append("  end;\r\n");
//        } else
        destroy.append("  F"+getTitle(s)+".free;\r\n");
        if (enumNames.contains(tn)) {         
          workingComposerX.append("  ComposeEnum(xml, '"+e.getName()+"', elem."+getTitle(s)+", CODES_"+tn+");\r\n");
          workingComposerJ.append("  ComposeEnum(json, '"+e.getName()+"', elem."+getTitle(s)+", CODES_"+tn+");\r\n");
        } else {
          workingComposerX.append("  Compose"+tn+"(xml, '"+e.getName()+"', elem."+getTitle(s)+");\r\n");
          workingComposerJ.append("  Compose"+tn+"(json, '"+e.getName()+"', elem."+getTitle(s)+");\r\n");        
        }
      }
      else {
        impl.append("Procedure "+cn+".Set"+getTitle(s)+"(value : "+tn+");\r\nbegin\r\n  F"+getTitle(s)+".free;\r\n  F"+getTitle(s)+" := value;\r\nend;\r\n\r\n");
        if (simpleTypes.containsKey(tn)) {
          String sn = simpleTypes.get(tn);
          if (sn.equals("String")) {
            impl.append("Function "+cn+".Get"+getTitle(s)+"ST : "+sn+";\r\nbegin\r\n  if F"+getTitle(s)+" = nil then\r\n    result := ''\r\n  else\r\n    result := "+getTitle(s)+".value;\r\nend;\r\n\r\n");
            impl.append("Procedure "+cn+".Set"+getTitle(s)+"ST(value : "+sn+");\r\nbegin\r\n  if value <> '' then\r\n  begin\r\n    if F"+getTitle(s)+" = nil then\r\n      F"+getTitle(s)+" := "+tn+".create;\r\n    F"+getTitle(s)+".value := value\r\n  end\r\n  else if F"+getTitle(s)+" <> nil then\r\n    F"+getTitle(s)+".value := '';\r\nend;\r\n\r\n");
          } else {
            impl.append("Function "+cn+".Get"+getTitle(s)+"ST : "+sn+";\r\nbegin\r\n  if F"+getTitle(s)+" = nil then\r\n    result := nil\r\n  else\r\n    result := "+getTitle(s)+".value;\r\nend;\r\n\r\n");
            impl.append("Procedure "+cn+".Set"+getTitle(s)+"ST(value : "+sn+");\r\nbegin\r\n  if value <> nil then\r\n  begin\r\n    if F"+getTitle(s)+" = nil then\r\n      F"+getTitle(s)+" := "+tn+".create;\r\n    F"+getTitle(s)+".value := value\r\n  end\r\n  else if F"+getTitle(s)+" <> nil then\r\n    F"+getTitle(s)+".value := nil;\r\nend;\r\n\r\n");
          }
        }
        destroy.append("  F"+getTitle(s)+".free;\r\n");
        assign.append("  "+s+" := "+cn+"(oSource)."+s+".Clone;\r\n");
        getkids.append("  if (child_name = '"+getElementName(e.getName())+"') Then\r\n     list.add("+getTitle(s)+".Link);\r\n");
        getprops.append("  oList.add(TFHIRProperty.create(self, '"+e.getName()+"', '"+e.typeCode()+"', "+propV+"));{2}\r\n");
        if (e.getName().contains("[x]") && e.getTypes().size() > 1) {
          String pfx = e.getName().replace("[x]", "");
          int t = e.getTypes().size();
          int i = 0;
          for (TypeRef td : e.getTypes()) {
            if (td.hasParams()) {
              for (String ptn : td.getParams()) {
                workingParserX.append("      else if (child.baseName = '"+pfx+getTitle(td.getName())+"_"+getTitle(ptn)+"') then\r\n        result."+s+" := Parse"+getTitle(td.getName())+"_"+getTitle(ptn)+"(child) {a}\r\n");
                workingComposerX.append("  "+(i==0 ? "if" : "else if")+" elem."+s+" is TFhir"+getTitle(td.getName())+"_"+getTitle(ptn)+" {2} then\r\n    Compose"+getTitle(td.getName())+"_"+getTitle(ptn)+"(xml, '"+pfx+getTitle(td.getName())+"_"+getTitle(ptn)+"', TFhir"+getTitle(td.getName())+"_"+getTitle(ptn)+"(elem."+s+"))"+(i == t-1?";" : "")+"\r\n");
                workingParserJ.append("      else if (json.ItemName = '"+pfx+getTitle(td.getName())+"_"+getTitle(ptn)+"') then\r\n        result."+s+" := Parse"+getTitle(td.getName())+"_"+getTitle(ptn)+"\r\n");
                workingComposerJ.append("  "+(i==0 ? "if" : "else if")+" elem."+s+" is TFhir"+getTitle(td.getName())+"_"+getTitle(ptn)+" then\r\n    Compose"+getTitle(td.getName())+"_"+getTitle(ptn)+"(json, '"+pfx+getTitle(td.getName())+"_"+getTitle(ptn)+"', TFhir"+getTitle(td.getName())+"_"+getTitle(ptn)+"(elem."+s+"))"+(i == t-1?";" : "")+"\r\n");
              }
            }
            else { 
              workingParserX.append("      else if (child.baseName = '"+pfx+getTitle(td.getName())+"') then\r\n        result."+s+" := Parse"+getTitle(td.getName())+"(child)\r\n");
//              if (td.getName().equalsIgnoreCase("string")) {
//                workingComposerX.append("  "+(i==0 ? "if" : "else if")+" elem."+s+" is TFHIR"+getTitle(td.getName())+" {3}  then\r\n    Text(xml, '"+pfx+getTitle(td.getName())+"', TFHIR"+getTitle(td.getName())+"(elem."+s+").value)"+(i == t-1?";" : "")+"\r\n");
//                workingComposerJ.append("  "+(i==0 ? "if" : "else if")+" elem."+s+" is TFHIR"+getTitle(td.getName())+" then\r\n    Prop(json, '"+pfx+getTitle(td.getName())+"', TFHIR"+getTitle(td.getName())+"(elem."+s+").value)"+(i == t-1?";" : "")+"\r\n");
//              } else if (td.getName().equalsIgnoreCase("code")) {
//                  workingComposerX.append("  "+(i==0 ? "if" : "else if")+" elem."+s+" is TFHIRString {4} then\r\n    Text(xml, '"+pfx+getTitle(td.getName())+"', TFHIRString(elem."+s+").value)"+(i == t-1?";" : "")+"\r\n");
//                  workingComposerJ.append("  "+(i==0 ? "if" : "else if")+" elem."+s+" is TFHIRString then\r\n    Prop(json, '"+pfx+getTitle(td.getName())+"', TFHIRString(elem."+s+").value)"+(i == t-1?";" : "")+"\r\n");
//              } else if (td.getName().equalsIgnoreCase("boolean") ) {
//                  workingComposerX.append("  "+(i==0 ? "if" : "else if")+" elem."+s+" is TFHIR"+getTitle(td.getName())+" {5} then\r\n    Text(xml, '"+pfx+getTitle(td.getName())+"', BooleanToString(TFHIR"+getTitle(td.getName())+"(elem."+s+").value))"+(i == t-1?";" : "")+"\r\n");
//                  workingComposerJ.append("  "+(i==0 ? "if" : "else if")+" elem."+s+" is TFHIR"+getTitle(td.getName())+" then\r\n    Prop(json, '"+pfx+getTitle(td.getName())+"', BooleanToString(TFHIR"+getTitle(td.getName())+"(elem."+s+").value))"+(i == t-1?";" : "")+"\r\n");
//              } else {
                workingComposerX.append("  "+(i==0 ? "if" : "else if")+" elem."+s+" is "+getTypeName(td.getName(), true)+" {6} then\r\n    Compose"+getTitle(td.getName())+"(xml, '"+pfx+getTitle(td.getName())+"', "+getTypeName(td.getName(), true)+"(elem."+s+"))"+(i == t-1?";" : "")+"\r\n");
                workingComposerJ.append("  "+(i==0 ? "if" : "else if")+" elem."+s+" is "+getTypeName(td.getName(), true)+" then\r\n    Compose"+getTitle(td.getName())+"(json, '"+pfx+getTitle(td.getName())+"', "+getTypeName(td.getName(), true)+"(elem."+s+"))"+(i == t-1?";" : "")+"\r\n");
//              }
              workingParserJ.append("      else if (json.ItemName = '"+pfx+getTitle(td.getName())+"') then\r\n        result."+s+" := Parse"+getTitle(td.getName())+"\r\n");
            }
            i++;
          }
          
        } else if (!e.getName().equals("[type]") && !e.getName().contains("[x]")) {
          workingParserX.append("      else if (child.baseName = '"+e.getName()+"') then\r\n        result."+s+" := Parse"+parseName(tn)+"(child) {b}\r\n");
          workingComposerX.append("  Compose"+parseName(tn)+"(xml, '"+e.getName()+"', elem."+s+");\r\n");
          workingParserJ.append("      else if (json.ItemName = '"+e.getName()+"') then\r\n        result."+s+" := Parse"+parseName(tn)+"\r\n");
          workingComposerJ.append("  Compose"+parseName(tn)+"(json, '"+e.getName()+"', elem."+s+");\r\n");
        } else {
          String pfx = e.getName().contains("[x]") ? e.getName().replace("[x]", "") : "";
          int i = 0;
          for (DefinedCode cd : definitions.getPrimitives().values()) {
            workingParserX.append("      else if (child.baseName = '"+pfx+getTitle(cd.getCode())+"') then\r\n        result."+s+" := Parse"+getTitle(cd.getCode())+"(child) {c}\r\n");
            String ptn = "TFhir"+getTitle(cd.getCode());
//            if (cd.getCode().equals("base64Binary"))
//              ptn = "TFHIRBytes";
            workingComposerX.append("  "+(i > 0 ? "else " : "")+"if elem."+s+" is "+ptn+" {1} then\r\n    Compose"+ptn.substring(5)+"(xml, '"+pfx+getTitle(cd.getCode())+"', "+ptn+"(elem."+s+"))\r\n");
            workingParserJ.append("      else if (json.ItemName = '"+pfx+getTitle(cd.getCode())+"') then\r\n        result."+s+" := Parse"+getTitle(cd.getCode())+"\r\n");
            workingComposerJ.append("  "+(i > 0 ? "else " : "")+"if elem."+s+" is "+ptn+" then\r\n    Compose"+ptn.substring(5)+"(json, '"+pfx+getTitle(cd.getCode())+"', "+ptn+"(elem."+s+"))\r\n");
            i++;
          }
          for (ElementDefn ed : definitions.getTypes().values()) {
            if (ed.getTypes().get(0).getName().equals("GenericType")) {
              for (TypeRef td : definitions.getKnownTypes()) {
                if (td.getName().equals(ed.getName()) && td.hasParams()) {
                  for (String ptn : td.getParams()) {
                    workingParserX.append("      else if (child.baseName = '"+pfx+getTitle(ed.getName())+"_"+getTitle(ptn)+"')  then\r\n        result."+s+" := Parse"+getTitle(ed.getName())+"_"+getTitle(ptn)+"(child) {d}\r\n");
                    workingComposerX.append("  else if elem."+s+" is TFhir"+getTitle(ed.getName())+"_"+getTitle(ptn)+" {7} then\r\n   Compose"+getTitle(ed.getName())+"_"+getTitle(ptn)+"(xml, '"+pfx+getTitle(ed.getName())+"_"+getTitle(ptn)+"', TFhir"+getTitle(ed.getName())+"_"+getTitle(ptn)+"(elem."+s+"))\r\n");
                    workingParserJ.append("      else if (json.ItemName = '"+pfx+getTitle(ed.getName())+"_"+getTitle(ptn)+"') then\r\n        result."+s+" := Parse"+getTitle(ed.getName())+"_"+getTitle(ptn)+"\r\n");
                    workingComposerJ.append("  else if elem."+s+" is TFhir"+getTitle(ed.getName())+"_"+getTitle(ptn)+" then\r\n    Compose"+getTitle(ed.getName())+"_"+getTitle(ptn)+"(json, '"+pfx+getTitle(ed.getName())+"_"+getTitle(ptn)+"', TFhir"+getTitle(ed.getName())+"_"+getTitle(ptn)+"(elem."+s+"))\r\n");
                  }
                }
              }
            } else {
              workingParserX.append("      else if (child.baseName = '"+pfx+getTitle(ed.getName())+"') then\r\n        result."+s+" := Parse"+getTitle(ed.getName())+"(child) {e}\r\n");
              workingComposerX.append("  else if elem."+s+" is TFhir"+getTitle(ed.getName())+" {8} then\r\n    Compose"+getTitle(ed.getName())+"(xml, '"+pfx+getTitle(ed.getName())+"', TFhir"+getTitle(ed.getName())+"(elem."+s+"))\r\n");
              workingParserJ.append("      else if (json.ItemName = '"+pfx+getTitle(ed.getName())+"') then\r\n        result."+s+" := Parse"+getTitle(ed.getName())+"\r\n");
              workingComposerJ.append("  else if elem."+s+" is TFhir"+getTitle(ed.getName())+" then\r\n    Compose"+getTitle(ed.getName())+"(json, '"+pfx+getTitle(ed.getName())+"', TFhir"+getTitle(ed.getName())+"(elem."+s+"))\r\n");
            }
          }
          int t = definitions.getStructures().size();
          i = 0;
          for (ElementDefn ed : definitions.getStructures().values()) {
            workingParserX.append("      else if (child.baseName = '"+pfx+getTitle(ed.getName())+"') then\r\n        result."+s+" := Parse"+getTitle(ed.getName())+"(child) {f}\r\n");
            workingComposerX.append("  else if elem."+s+" is TFhir"+getTitle(ed.getName())+" {9} then\r\n    Compose"+getTitle(ed.getName())+"(xml, '"+pfx+getTitle(ed.getName())+"', TFhir"+getTitle(ed.getName())+"(elem."+s+"))"+(i < t-1 ? "" : ";")+"\r\n");
            workingParserJ.append("      else if (json.ItemName = '"+pfx+getTitle(ed.getName())+"') then\r\n        result."+s+" := Parse"+getTitle(ed.getName())+"\r\n");
            workingComposerJ.append("  else if elem."+s+" is TFhir"+getTitle(ed.getName())+" then\r\n    Compose"+getTitle(ed.getName())+"(json, '"+pfx+getTitle(ed.getName())+"', TFhir"+getTitle(ed.getName())+"(elem."+s+"))"+(i < t-1 ? "" : ";")+"\r\n");
            i++;
          }
        }
      }
    }

  }

  private String parseName(String tn) {
    return tn.startsWith("TFhir") ? tn.substring(5) : tn.substring(1);
  }

  private void defineList(String tn, String tnl, ClassCategory category, boolean isAbstract) {
    if (tnl.contains("{"))
      tnl = tnl.substring(0, tnl.indexOf("{"));
    if (tn.contains("{"))
      tn = tn.substring(0, tn.indexOf("{"));
    if (!lists.contains(tnl)) {
      lists.add(tn+"List");
      String tt = tn.substring(1);
      getCode(category).classFwds.add("  "+tn+"List = class;\r\n");
      getCode(category).classDefs.add(
        "  {@Class "+tn+"List\r\n"+
        "    A list of "+tt+"\r\n"+
        "  }\r\n"+
        "  {!.Net HL7Connect.Fhir."+tn.substring(5)+"List}\r\n"+
        "  "+tn+"List = class (TFHIRObjectList)\r\n"+
        "  private\r\n"+
        "    function GetItemN(index : Integer) : "+tn+";\r\n"+
        "    procedure SetItemN(index : Integer; value : "+tn+");\r\n"+
        "  public\r\n"+
        "    {!script hide}\r\n"+
        "    function Link : "+tn+"List; Overload;\r\n"+
        "    function Clone : "+tn+"List; Overload;\r\n"+
        "    {!script show}\r\n"+
        "    \r\n");
      if (!isAbstract)
      getCode(category).classDefs.add(
        "    {@member Append\r\n"+
        "      Add a "+tt+" to the end of the list.\r\n"+
        "    }\r\n"+
        "    function Append : "+tn+";\r\n");
      getCode(category).classDefs.add(
        "    \r\n"+
        "    {@member AddItem\r\n"+
        "      Add an already existing "+tt+" to the end of the list.\r\n"+
        "    }\r\n"+
        "    procedure AddItem(value : "+tn+");\r\n"+
        "    \r\n"+
        "    {@member IndexOf\r\n"+
        "      See if an item is already in the list. returns -1 if not in the list\r\n"+
        "    }\r\n"+
        "    \r\n"+
        "    {@member IndexOf\r\n"+
        "      See if an item is already in the list. returns -1 if not in the list\r\n"+
        "    }\r\n"+
        "    function IndexOf(value : "+tn+") : Integer;\r\n"+
        "    \r\n");
      if (!isAbstract)
        getCode(category).classDefs.add(
        "    {@member Insert\r\n"+
        "      Insert "+tt+" before the designated index (0 = first item)\r\n"+
        "    }\r\n"+
        "    function Insert(index : Integer) : "+tn+";\r\n"+
        "    \r\n");
        getCode(category).classDefs.add(
        "    {@member InsertItem\r\n"+
        "       Insert an existing "+tt+" before the designated index (0 = first item)\r\n"+
        "    }\r\n"+
        "    procedure InsertItem(index : Integer; value : "+tn+");\r\n"+
        "    \r\n"+
        "    {@member Item\r\n"+
        "       Get the iIndexth "+tt+". (0 = first item)\r\n"+
        "    }\r\n"+
        "    \r\n"+
        "    {@member Item\r\n"+
        "       Get the iIndexth "+tt+". (0 = first item)\r\n"+
        "    }\r\n"+
        "    procedure SetItemByIndex(index : Integer; value : "+tn+");\r\n"+
        "    \r\n"+
        "    {@member Count\r\n"+
        "      The number of items in the collection\r\n"+
        "    }\r\n"+
        "    function Item(index : Integer) : "+tn+";\r\n"+
        "    \r\n"+
        "    {@member Count\r\n"+
        "      The number of items in the collection\r\n"+
        "    }\r\n"+
        "    function Count : Integer; Overload;\r\n"+
        "    \r\n"+
        "    {@member remove\r\n"+
        "      Remove the indexth item. The first item is index 0.\r\n"+
        "    }\r\n"+
        "    procedure Remove(index : Integer);\r\n"+
        "    {@member ClearItems\r\n"+
        "      Remove All Items from the list\r\n"+
        "    }\r\n"+
        "    procedure ClearItems;\r\n"+
        "    \r\n"+
        "    Property "+Utilities.pluralizeMe(tt)+"[index : Integer] : "+tn+" read GetItemN write SetItemN; default;\r\n"+
        "  End;\r\n"+
        "\r\n"  
          );
      getCode(category).classImpls.add(
        "{ "+tn+"List }\r\n"+
        "procedure "+tn+"List.AddItem(value: "+tn+");\r\n"+
        "begin\r\n"+
        "  assert(value.ClassName = '"+tn+"', 'Attempt to add an item of type '+value.ClassName+' to a List of "+tn+"');\r\n"+
        "  add(value);\r\n"+
        "end;\r\n"+
        "\r\n");
        if (!isAbstract)
          getCode(category).classImpls.add(
        "function "+tn+"List.Append: "+tn+";\r\n"+
        "begin\r\n"+
        "  result := "+tn+".create;\r\n"+
        "  try\r\n"+
        "    add(result.Link);\r\n"+
        "  finally\r\n"+
        "    result.free;\r\n"+
        "  end;\r\n"+
        "end;\r\n"+
        "\r\n");
        getCode(category).classImpls.add(
        "procedure "+tn+"List.ClearItems;\r\n"+
        "begin\r\n"+
        "  Clear;\r\n"+
        "end;\r\n"+
        "\r\n"+
        "function "+tn+"List.Clone: "+tn+"List;\r\n"+
        "begin\r\n"+
        "  result := "+tn+"List(inherited Clone);\r\n"+
        "end;\r\n"+
        "\r\n"+
        "function "+tn+"List.Count: Integer;\r\n"+
        "begin\r\n"+
        "  result := Inherited Count;\r\n"+
        "end;\r\n"+
        "\r\n"+
        "function "+tn+"List.GetItemN(index: Integer): "+tn+";\r\n"+
        "begin\r\n"+
        "  result := "+tn+"(ObjectByIndex[index]);\r\n"+
        "end;\r\n"+
        "\r\n"+
        "function "+tn+"List.IndexOf(value: "+tn+"): Integer;\r\n"+
        "begin\r\n"+
        "  result := IndexByReference(value);\r\n"+
        "end;\r\n"+
        "\r\n");
        if (!isAbstract)
        getCode(category).classImpls.add(
        "function "+tn+"List.Insert(index: Integer): "+tn+";\r\n"+
        "begin\r\n"+
        "  result := "+tn+".create;\r\n"+
        "  try\r\n"+
        "    inherited insert(index, result);\r\n"+
        "  finally\r\n"+
        "    result.free;\r\n"+
        "  end;\r\n"+
        "end;\r\n"+
        "\r\n");
        getCode(category).classImpls.add(
        "procedure "+tn+"List.InsertItem(index: Integer; value: "+tn+");\r\n"+
        "begin\r\n"+
        "  assert(value is "+tn+");\r\n"+
        "  Inherited Insert(index, value);\r\n"+
        "end;\r\n"+
        "\r\n"+
        "function "+tn+"List.Item(index: Integer): "+tn+";\r\n"+
        "begin\r\n"+
        "  result := "+tn+"(ObjectByIndex[index]);\r\n"+
        "end;\r\n"+
        "\r\n"+
        "function "+tn+"List.Link: "+tn+"List;\r\n"+
        "begin\r\n"+
        "  result := "+tn+"List(inherited Link);\r\n"+
        "end;\r\n"+
        "\r\n"+
        "procedure "+tn+"List.Remove(index: Integer);\r\n"+
        "begin\r\n"+
        "  DeleteByIndex(index);\r\n"+
        "end;\r\n"+
        "\r\n"+
        "procedure "+tn+"List.SetItemByIndex(index: Integer; value: "+tn+");\r\n"+
        "begin\r\n"+
        "  assert(value is "+tn+");\r\n"+
        "  "+Utilities.pluralizeMe(tt)+"[index] := value;\r\n"+
        "end;\r\n"+
        "\r\n"+
        "procedure "+tn+"List.SetItemN(index: Integer; value: "+tn+");\r\n"+
        "begin\r\n"+
        "  assert(value is "+tn+");\r\n"+
        "  ObjectByIndex[index] := value;\r\n"+
        "end;\r\n"        
          ); 
    }
  }

  private boolean typeIsSimple(String tn) {
    if (tn == null)
      return false;
    return tn.equals("String") || tn.equals("Integer") || tn.equals("Boolean") || tn.equals("TDateAndTime") || tn.equals("TFhirXHtmlNode")  || tn.equals("TXmlIdReference") || enumNames.contains(tn);
  }

  private String getTitle(String name) {
    if (name.length() < 2)
      return name.toUpperCase();
    else
      return name.substring(0, 1).toUpperCase()+ name.substring(1);
  }

  private String getElementName(String name) {
    if (GeneratorUtils.isDelphiReservedWord(name))
      return name+"_";
    return name.replace("[x]", "").replace("[type]", "value");
  }

  private String getTypeName(ElementDefn e, boolean hasId) throws Exception {
    if (e.getTypes().size() > 1) {
      return "TFhirType";
    } else if (e.getTypes().size() == 0) {
      throw new Exception("not supported");
    } else {
      return getTypename(e.getTypes().get(0), hasId);
    }
  }

  private String getTypename(TypeRef type, boolean complex) throws Exception {
    if (type.getParams().size() == 1) {     
      if (type.isResourceReference())
        return "TFhirResourceReference{"+getTypeName(type.getParams().get(0), complex)+"}";
      else if (type.getName().equals("Interval"))
        return "TInterval_"+type.getParams().get(0);
      else
        throw new Exception("not supported: "+type.summary());
    } else if (type.getParams().size() > 1) {
      if (type.isResourceReference())
        return "TFhirResourceReference{Resource}";
      else
        throw new Exception("not supported");
    } else {
      return getTypeName(type.getName(), complex);
    }
  }

  private String getTypeName(String tn, boolean complex) {
    if (tn == null) {
      return "";
//    } else if (tn.equals("string")) {
//      return complex ? "TFHIRString" : "String";
    } else if (tn.equals("xml:lang")) {
      return "TFhirString";
//    } else if (tn.equals("id")) {
//      return complex ? "TFHIRString" : "String";
//    } else if (tn.equals("code")) {
//      return complex ? "TFHIRString" : "String";
//    } else if (tn.equals("oid")) {
//      return complex ? "TFHIROid" : "String";
//    } else if (tn.equals("integer")) {
//      return complex ? "TFHIRInteger" : "Integer";
//    } else if (tn.equals("instant")) {
//      return complex ? "TFHIRInstant" : "TDateAndTime";
//    } else if (tn.equals("boolean")) {
//      return complex ? "TFHIRBoolean" : "Boolean";
//    } else if (tn.equals("dateTime")) {
//      return complex ? "TFHIRString" : "String";
//    } else if (tn.equals("date")) {
//      return complex ? "TFHIRDate" : "String";
//    } else if (tn.equals("uri")) {
//      return complex ? "TFHIRString" : "String";
//    } else if (tn.equals("decimal")) {
//      return complex ? "TFHIRDecimal" : "TSmartDecimal";      
    } else if (tn.equals("xhtml")) {
      return "TFhirXHtmlNode"; 
    } else if (tn.equals("idref")) {
      return "TFhirString";
//    } else if (tn.equals("base64Binary")) {
//      return "TFHIRBuffer";
    } else if (tn.equals("*")) {
      return "TFhirType";
    } else if (tn.equals("Any")) {
      return "Resource";
    } else if (definitions.getConstraints().containsKey(tn)) {
      return getTypeName(definitions.getConstraints().get(tn).getComment(), complex);
    } else {
      return "TFhir"+getTitle(tn);
    }
  }
  
  @Override
public String getName() {
    return "delphi";
  }

  public void genConstraint(DefinedCode c) {
    prsrdefX.append("    function Parse"+c.getCode()+"(element : IXmlDomElement) : TFhir"+c.getCode()+";\r\n");
    srlsdefX.append("    procedure Compose"+c.getCode()+"(xml : TMsXmlBuilder; name : string; elem : TFhir"+c.getCode()+");\r\n");
    prsrdefJ.append("    function Parse"+c.getCode()+" : TFhir"+c.getCode()+";\r\n");
    srlsdefJ.append("    procedure Compose"+c.getCode()+"(json : TJSONWriter; name : string; elem : TFhir"+c.getCode()+");\r\n");
    defCodeType.classDefs.add("  TFhir"+c.getCode()+" = TFhir"+c.getComment()+";\r\n");
    prsrImpl.append("function TFHIRXmlParser.Parse"+c.getCode()+"(element : IXmlDomElement) : TFhir"+c.getCode()+";\r\nbegin\r\n  result := Parse"+c.getComment()+"(element);\r\nend;\r\n\r\n");
    prsrImpl.append("procedure TFHIRXmlComposer.Compose"+c.getCode()+"(xml : TMsXmlBuilder; name : string; elem : TFhir"+c.getCode()+");\r\nbegin\r\n  Compose"+c.getComment()+"(xml, name, elem);\r\nend;\r\n\r\n");
    prsrImpl.append("function TFHIRJsonParser.Parse"+c.getCode()+" : TFhir"+c.getCode()+";\r\nbegin\r\n  result := Parse"+c.getComment()+";\r\nend;\r\n\r\n");
    prsrImpl.append("procedure TFHIRJsonComposer.Compose"+c.getCode()+"(json : TJSONWriter; name : string; elem : TFhir"+c.getCode()+");\r\nbegin\r\n  Compose"+c.getComment()+"(json, name, elem);\r\nend;\r\n\r\n");
  }
  
  
  private void generatePrimitive(DefinedCode t, String parent, boolean isEnum, boolean derived) {
    StringBuilder def = new StringBuilder();
    String tn = Utilities.capitalize(t.getCode());
    String pn = "String";
    if (tn.equals("Date") || tn.equals("DateTime") || tn.equals("Instant"))
      pn = "TDateAndTime";
    
    simpleTypes.put("TFhir"+tn, pn);
    def.append("  {@Class TFhir"+tn+" : "+parent+"\r\n");
    def.append("    a complex string - has an xmlId attribute, and a dataAbsentReason.\r\n");
    def.append("    \r\n");
    def.append("    Used where a FHIR element is a string, and may have a dataAbsentReason\r\n");
    def.append("  }\r\n");
    def.append("  {!.Net HL7Connect.Fhir."+tn+"}\r\n");
    def.append("  TFhir"+tn+" = class ("+parent+")\r\n");
    def.append("  Private\r\n");
    if (!derived) {
    	def.append("    FValue: "+pn+";\r\n");
    	def.append("    procedure setValue(value: "+pn+");\r\n");
    	def.append("  protected\r\n");
    	def.append("    Procedure GetChildrenByName(child_name : string; list : TFHIRObjectList); override;\r\n");
    	def.append("    Procedure ListProperties(oList : TFHIRPropertyList; bInheritedProperties : Boolean); Override;\r\n");
    }
    def.append("  Public\r\n");
    def.append("    Constructor Create(value : "+pn+"); overload;\r\n");
    def.append("    Destructor Destroy; override;\r\n");
    def.append("    \r\n");
    def.append("    {!script hide}\r\n");
    def.append("    Function Link : TFhir"+tn+"; Overload;\r\n");
    def.append("    Function Clone : TFhir"+tn+"; Overload;\r\n");
    if (!derived) {
    	def.append("    procedure Assign(oSource : TAdvObject); override;\r\n");
    }
    def.append("    {!script show}\r\n");
    if (!derived) {
    	def.append("  Published\r\n");
    	def.append("    {@member value\r\n");
    	def.append("      The actual value of the "+t.getCode()+"\r\n");
    	def.append("    }\r\n");
    	def.append("    property value : "+pn+" read FValue write SetValue;\r\n");
    }
    def.append("  End;    \r\n");
    def.append("\r\n");

    StringBuilder impl2 = new StringBuilder();
    impl2.append("{ TFhir"+tn+" }\r\n\r\n");

    impl2.append("Constructor TFhir"+tn+".Create(value : "+pn+");\r\n");
    impl2.append("begin\r\n");
    impl2.append("  Create;\r\n");
    impl2.append("  FValue := value;\r\n");
    impl2.append("end;\r\n\r\n");
    
    impl2.append("Destructor TFhir"+tn+".Destroy;\r\n");
    impl2.append("begin\r\n");
    if (!derived) {
    if (!pn.equals("String"))
      impl2.append("  FValue.free;\r\n");
    }
    impl2.append("  inherited;\r\n");
    impl2.append("end;\r\n\r\n");
    
    if (!derived) {

    	impl2.append("procedure TFhir"+tn+".GetChildrenByName(child_name : string; list : TFHIRObjectList);\r\n");
    	impl2.append("begin\r\n");
    	impl2.append("  inherited;\r\n");
    	impl2.append("  if child_name = 'value' then\r\n    list.add(TFHIRObjectText.create(value));\r\n");
    	impl2.append("end;\r\n\r\n");
    	impl2.append("procedure TFhir"+tn+".ListProperties(oList: TFHIRPropertyList; bInheritedProperties: Boolean);\r\n");
    	impl2.append("begin\r\n");
    	impl2.append("  inherited;\r\n");
    	if (!pn.equals("String"))
    		impl2.append("  oList.add(TFHIRProperty.create(self, 'value', '"+t.getCode()+"', FValue.toString));\r\n");
    	else
    		impl2.append("  oList.add(TFHIRProperty.create(self, 'value', '"+t.getCode()+"', FValue));\r\n");
    	impl2.append("end;\r\n\r\n");


    	impl2.append("procedure TFhir"+tn+".Assign(oSource : TAdvObject);\r\n");
    	impl2.append("begin\r\n");
    	impl2.append("  inherited;\r\n");
    	impl2.append("  FXmlId := TFhirElement(oSource).FXmlId;\r\n");
    	impl2.append("  if TFhirElement(oSource).HasExtensions then\r\n    extensionList.assign(TFhirElement(oSource).extensionList)\r\n"+
    			"  else if FExtensionList <> nil then\r\n  begin\r\n    FExtensionList.free;\r\n    FExtensionList := nil;\r\n  end;\r\n");
    	impl2.append("end;\r\n\r\n");
    }

    impl2.append("function TFhir"+tn+".Link : TFhir"+tn+";\r\n");
    impl2.append("begin\r\n");
    impl2.append("  result := TFhir"+tn+"(inherited Link);\r\n");
    impl2.append("end;\r\n\r\n");
    impl2.append("function TFhir"+tn+".Clone : TFhir"+tn+";\r\n");
    impl2.append("begin\r\n");
    impl2.append("  result := TFhir"+tn+"(inherited Clone);\r\n");
    impl2.append("end;\r\n\r\n");
    if (!derived) {
    	impl2.append("procedure TFhir"+tn+".setValue(value : "+pn+");\r\n");
    	impl2.append("begin\r\n");
    	if (!pn.equals("String")) 
    		impl2.append("  FValue.free;\r\n");
    	impl2.append("  FValue := value;\r\n");
    	impl2.append("end;\r\n\r\n");
    }    

    if (isEnum) {
      prsrdefX.append("    function Parse"+tn+"(Const aNames : Array Of String; element : IXmlDomElement) : TFhir"+tn+";\r\n");
      prsrImpl.append("function TFHIRXmlParser.Parse"+tn+"(Const aNames : Array Of String; element : IXmlDomElement) : TFhir"+tn+";\r\n");
      prsrImpl.append("var\r\n");
      prsrImpl.append("  child : IXMLDOMElement;\r\n");
      prsrImpl.append("begin\r\n");
      prsrImpl.append("  result := TFhir"+tn+".create;\r\n");
      prsrImpl.append("  try\r\n");
      prsrImpl.append("    ParseElementAttributes(result, element);\r\n");
      prsrImpl.append("    result.value := GetAttribute(element, 'value');\r\n");
      prsrImpl.append("    if StringArrayIndexOf(aNames, result.value) < 0 then\r\n");
      prsrImpl.append("      raise Exception.create('unknown code: '+result.value);\r\n");
      prsrImpl.append("    child := FirstChild(element);\r\n");
      prsrImpl.append("    while (child <> nil) do\r\n");
      prsrImpl.append("    begin\r\n");
      prsrImpl.append("      if Not ParseElementChild(result, child) then\r\n");
      prsrImpl.append("         UnknownContent(child);\r\n");
      prsrImpl.append("      child := NextSibling(child);\r\n");
      prsrImpl.append("    end;\r\n");
      prsrImpl.append("    closeOutElement(result, element);\r\n\r\n");
      prsrImpl.append("    result.link;\r\n");
      prsrImpl.append("  finally\r\n");
      prsrImpl.append("    result.free;\r\n");
      prsrImpl.append("  end;\r\n");
      prsrImpl.append("end;\r\n\r\n");
      
      prsrdefJ.append("    Function Parse"+tn+"(Const aNames : Array Of String) : TFhir"+tn+";\r\n");
      prsrImpl.append("function TFHIRJsonParser.Parse"+tn+"(Const aNames : Array Of String) : TFhir"+tn+";\r\n");
      prsrImpl.append("begin\r\n");
      prsrImpl.append("  FJson.next;\r\n");
      prsrImpl.append("  result := TFhir"+tn+".Create;\r\n");
      prsrImpl.append("  try\r\n");
      prsrImpl.append("    while (FJson.ItemType <> jpitEnd) do\r\n");
      prsrImpl.append("    begin\r\n");
      prsrImpl.append("      if (FJson.ItemName = 'value') then\r\n");
      prsrImpl.append("        result.value := FJson.ItemValue\r\n");
      prsrImpl.append("      else if not ParseElementProperty(result) then\r\n");
      prsrImpl.append("         UnknownContent;\r\n");
      prsrImpl.append("      FJson.next;\r\n");
      prsrImpl.append("    end;\r\n");
      prsrImpl.append("    if StringArrayIndexOf(aNames, result.value) < 0 then\r\n");
      prsrImpl.append("      raise Exception.create('unknown code: '+result.value);\r\n");
      prsrImpl.append("    result.Link;\r\n");
      prsrImpl.append("  finally\r\n");
      prsrImpl.append("    result.Free;\r\n");
      prsrImpl.append("  end;\r\n");
      prsrImpl.append("end;\r\n\r\n");


      srlsdefX.append("    Procedure Compose"+tn+"(xml : TMsXmlBuilder; name : String; value : TFhir"+tn+"; Const aNames : Array Of String);\r\n");
      prsrImpl.append("Procedure TFHIRXmlComposer.Compose"+tn+"(xml : TMsXmlBuilder; name : String; value : TFhir"+tn+"; Const aNames : Array Of String);\r\n");
      prsrImpl.append("begin\r\n");
      prsrImpl.append("  if (value = nil) then\r\n");
      prsrImpl.append("    exit;\r\n");
      prsrImpl.append("  composeElementAttributes(xml, value);\r\n");
      prsrImpl.append("  attribute(xml, 'value', value.value);\r\n");
      prsrImpl.append("  xml.open(name);\r\n");
      prsrImpl.append("  composeElementChildren(xml, value);\r\n");
      prsrImpl.append("  closeOutElement(xml, value);\r\n");
      prsrImpl.append("  xml.close(name);\r\n");
      prsrImpl.append("end;\r\n\r\n");
      srlsdefJ.append("    Procedure Compose"+tn+"(json : TJSONWriter; name : String; value : TFhir"+tn+"; Const aNames : Array Of String);\r\n");
      prsrImpl.append("Procedure TFHIRJsonComposer.Compose"+tn+"(json : TJSONWriter; name : String; value : TFhir"+tn+"; Const aNames : Array Of String);\r\n");
      prsrImpl.append("begin\r\n");
      prsrImpl.append("  if (value = nil) then\r\n");
      prsrImpl.append("    exit;\r\n");
      prsrImpl.append("  json.valueObject(name);\r\n");
      prsrImpl.append("  ComposeElementProperties(json, value);\r\n");
      prsrImpl.append("  prop(json, 'value', value.value);\r\n");
      prsrImpl.append("  json.finishObject;\r\n");
      prsrImpl.append("end;\r\n\r\n");
    } else {
      prsrdefX.append("    function Parse"+tn+"(element : IXmlDomElement) : TFhir"+tn+";\r\n");
      prsrImpl.append("function TFHIRXmlParser.Parse"+tn+"(element : IXmlDomElement) : TFhir"+tn+";\r\n");
      prsrImpl.append("var\r\n");
      prsrImpl.append("  child : IXMLDOMElement;\r\n");
      prsrImpl.append("begin\r\n");
      prsrImpl.append("  result := TFhir"+tn+".create;\r\n");
      prsrImpl.append("  try\r\n");
      prsrImpl.append("    ParseElementAttributes(result, element);\r\n");
      if (!pn.equals("String"))
        prsrImpl.append("    result.value := to"+pn+"(GetAttribute(element, 'value'));\r\n");
      else
        prsrImpl.append("    result.value := GetAttribute(element, 'value');\r\n");
      prsrImpl.append("    child := FirstChild(element);\r\n");
      prsrImpl.append("    while (child <> nil) do\r\n");
      prsrImpl.append("    begin\r\n");
      prsrImpl.append("      if Not ParseElementChild(result, child) then\r\n");
      prsrImpl.append("         UnknownContent(child);\r\n");
      prsrImpl.append("      child := NextSibling(child);\r\n");
      prsrImpl.append("    end;\r\n");
      prsrImpl.append("    closeOutElement(result, element);\r\n\r\n");
      prsrImpl.append("    result.link;\r\n");
      prsrImpl.append("  finally\r\n");
      prsrImpl.append("    result.free;\r\n");
      prsrImpl.append("  end;\r\n");
      prsrImpl.append("end;\r\n\r\n");


      prsrdefJ.append("    Function Parse"+tn+" : TFhir"+tn+";\r\n");
      prsrImpl.append("function TFHIRJsonParser.Parse"+tn+": TFhir"+tn+";\r\n");
      prsrImpl.append("begin\r\n");
      prsrImpl.append("  FJson.next;\r\n");
      prsrImpl.append("  result := TFhir"+tn+".Create;\r\n");
      prsrImpl.append("  try\r\n");
      prsrImpl.append("    while (FJson.ItemType <> jpitEnd) do\r\n");
      prsrImpl.append("    begin\r\n");
      prsrImpl.append("      if (FJson.ItemName = 'value') then\r\n");
      if (!pn.equals("String"))
        prsrImpl.append("        result.value := to"+pn+"(FJson.ItemValue)\r\n");
      else
        prsrImpl.append("        result.value := FJson.ItemValue\r\n");
      prsrImpl.append("      else if not ParseElementProperty(result) then\r\n");
      prsrImpl.append("         UnknownContent;\r\n");
      prsrImpl.append("      FJson.next;\r\n");
      prsrImpl.append("    end;\r\n");
      prsrImpl.append("    result.Link;\r\n");
      prsrImpl.append("  finally\r\n");
      prsrImpl.append("    result.Free;\r\n");
      prsrImpl.append("  end;\r\n");
      prsrImpl.append("end;\r\n\r\n");
      srlsdefX.append("    Procedure Compose"+tn+"(xml : TMsXmlBuilder; name : String; value : TFhir"+tn+");\r\n");
      prsrImpl.append("Procedure TFHIRXmlComposer.Compose"+tn+"(xml : TMsXmlBuilder; name : String; value : TFhir"+tn+");\r\n");
      prsrImpl.append("begin\r\n");
      if (!pn.equals("String"))
        prsrImpl.append("  if (value = nil) or (value.value = nil) then\r\n");
      else
        prsrImpl.append("  if (value = nil) or (value.value = '') then\r\n");
      prsrImpl.append("    exit;\r\n");
      prsrImpl.append("  composeElementAttributes(xml, value);\r\n");
      if (!pn.equals("String"))
        prsrImpl.append("  attribute(xml, 'value', toString(value.value));\r\n");
      else
        prsrImpl.append("  attribute(xml, 'value', value.value);\r\n");
      prsrImpl.append("  xml.open(name);\r\n");
      prsrImpl.append("  composeElementChildren(xml, value);\r\n");
      prsrImpl.append("  closeOutElement(xml, value);\r\n");
      prsrImpl.append("  xml.close(name);\r\n");
      prsrImpl.append("end;\r\n\r\n");
      srlsdefJ.append("    Procedure Compose"+tn+"(json : TJSONWriter; name : String; value : TFhir"+tn+");\r\n");
      prsrImpl.append("Procedure TFHIRJsonComposer.Compose"+tn+"(json : TJSONWriter; name : String; value : TFhir"+tn+");\r\n");
      prsrImpl.append("begin\r\n");
      if (!pn.equals("String"))
        prsrImpl.append("  if (value = nil) or (value.value = nil) then\r\n");
      else
        prsrImpl.append("  if (value = nil) or (value.value = '') then\r\n");
      prsrImpl.append("    exit;\r\n");
      prsrImpl.append("  json.valueObject(name);\r\n");
      prsrImpl.append("  ComposeElementProperties(json, value);\r\n");
      if (!pn.equals("String"))
        prsrImpl.append("  prop(json, 'value', toString(value.value));\r\n");
      else
        prsrImpl.append("  prop(json, 'value', value.value);\r\n");
      prsrImpl.append("  json.finishObject;\r\n");
      prsrImpl.append("end;\r\n\r\n");
    }

    defCodeType.classDefs.add(def.toString());
    defCodeType.classImpls.add(impl2.toString());
    defCodeType.classFwds.add("  TFhir"+tn+" = class;\r\n");
    defineList("TFhir"+tn, "TFhir"+tn+"List", ClassCategory.Type, false);
  }
  
  private void generateElement() {
    StringBuilder def = new StringBuilder();

    def.append("  {@Class TFhirElement : TFHIRBase\r\n");
    def.append("    Base Element Definition - extensions, ids\r\n");
    def.append("  }\r\n");
    def.append("  {!.Net HL7Connect.Fhir.Element}\r\n");
    def.append("  TFhirElement = {abstract} class (TFHIRBase)\r\n");
    def.append("  private\r\n");
    def.append("    FXmlId: String;\r\n");
    def.append("    FExtensionList : TFhirExtensionList;\r\n");
    def.append("    function GetExtensionList: TFhirExtensionList;\r\n");
    def.append("  protected\r\n");
    def.append("    Procedure GetChildrenByName(child_name : string; list : TFHIRObjectList); override;\r\n");
    def.append("    Procedure ListProperties(oList : TFHIRPropertyList; bInheritedProperties : Boolean); Override;\r\n");
    def.append("  public\r\n");
    def.append("    destructor Destroy; override;\r\n");
    def.append("    {!script hide}\r\n");
    def.append("    procedure Assign(oSource : TAdvObject); override;\r\n");
    def.append("    function Link : TFhirElement; overload;\r\n");
    def.append("    function Clone : TFhirElement; overload;\r\n");
    def.append("    function HasExtensions : Boolean;\r\n");
    def.append("    {!script show}\r\n");
    def.append("  published\r\n");
    def.append("    {@member xmlId\r\n");
    def.append("      the value of the xml id attribute, if present.\r\n");
    def.append("    }\r\n");
    def.append("    property xmlId : String read FXmlId write FXmlId;\r\n");
    def.append("    {@member ExtensionList\r\n");
    def.append("      Extensions on this value\r\n");
    def.append("    }\r\n");
    def.append("    property ExtensionList : TFhirExtensionList read GetExtensionList;\r\n");
    def.append("  end;\r\n");
    def.append("  \r\n");
    def.append("\r\n");
    def.append("  {@Class TFhirType : TFhirElement\r\n");
    def.append("    A base FHIR type - (polymorphism support)\r\n");
    def.append("  }\r\n");
    def.append("  {!.Net HL7Connect.Fhir.Type}\r\n");
    def.append("  TFhirType = class (TFhirElement)\r\n");
    def.append("  Public\r\n");
    def.append("    {!script hide}\r\n");
    def.append("    Function Link : TFhirType; Overload;\r\n");
    def.append("    Function Clone : TFhirType; Overload;\r\n");
    def.append("    {!script show}\r\n");
    def.append("  End;\r\n");   
    def.append("  \r\n");
    
    StringBuilder impl2 = new StringBuilder();
    impl2.append("{ TFhirElement }\r\n\r\n");

    impl2.append("destructor TFhirElement.Destroy;\r\n");
    impl2.append("begin\r\n");
    impl2.append("  FExtensionList.Free;\r\n");
    impl2.append("  inherited;\r\n");
    impl2.append("end;\r\n\r\n");
    
    impl2.append("procedure TFhirElement.GetChildrenByName(child_name : string; list : TFHIRObjectList);\r\n");
    impl2.append("begin\r\n");
    impl2.append("  inherited;\r\n");
    impl2.append("  if child_name = '@id' then\r\n    list.add(TFHIRObjectText.create(FXmlId));\r\n");
    impl2.append("  if (child_name = 'extension') Then\r\n     list.addAll(FExtensionList);\r\n");
    impl2.append("end;\r\n\r\n");
    impl2.append("procedure TFhirElement.ListProperties(oList: TFHIRPropertyList; bInheritedProperties: Boolean);\r\n");
    impl2.append("begin\r\n");
    impl2.append("  inherited;\r\n");
    impl2.append("  oList.add(TFHIRProperty.create(self, 'xml:id', 'string', FXmlId));\r\n");
    impl2.append("  oList.add(TFHIRProperty.create(self, 'extension', 'Extension', FExtensionList.Link));\r\n");
    impl2.append("end;\r\n\r\n");
    
    
    impl2.append("procedure TFhirElement.Assign(oSource : TAdvObject);\r\n");
    impl2.append("begin\r\n");
    impl2.append("  inherited;\r\n");
    impl2.append("  FXmlId := TFhirElement(oSource).FXmlId;\r\n");
    impl2.append("  if TFhirElement(oSource).HasExtensions then\r\n    extensionList.assign(TFhirElement(oSource).extensionList)\r\n"+
    "  else if FExtensionList <> nil then\r\n  begin\r\n    FExtensionList.free;\r\n    FExtensionList := nil;\r\n  end;\r\n");
    impl2.append("end;\r\n\r\n");
    
    impl2.append("function TFhirElement.Link : TFhirElement;\r\n");
    impl2.append("begin\r\n");
    impl2.append("  result := TFhirElement(inherited Link);\r\n");
    impl2.append("end;\r\n\r\n");
    impl2.append("function TFhirElement.Clone : TFhirElement;\r\n");
    impl2.append("begin\r\n");
    impl2.append("  result := TFhirElement(inherited Clone);\r\n");
    impl2.append("end;\r\n\r\n");
    impl2.append("function TFhirElement.GetExtensionList : TFhirExtensionList;\r\n");
    impl2.append("begin\r\n");
    impl2.append("  if FExtensionList = nil then\r\n    FExtensionList := TFhirExtensionList.Create;\r\n  result := FExtensionList;\r\n");
    impl2.append("end;\r\n\r\n");
    impl2.append("function TFhirElement.HasExtensions : boolean;\r\n");
    impl2.append("begin\r\n");
    impl2.append("  result := (FExtensionList <> nil) and (FExtensionList.count > 0);\r\n");
    impl2.append("end;\r\n\r\n");

    impl2.append("{ TFhirType }\r\n\r\n");
    impl2.append("function TFhirType.Link : TFhirType;\r\n");
    impl2.append("begin\r\n");
    impl2.append("  result := TFhirType(inherited Link);\r\n");
    impl2.append("end;\r\n\r\n");
    impl2.append("function TFhirType.Clone : TFhirType;\r\n");
    impl2.append("begin\r\n");
    impl2.append("  result := TFhirType(inherited Clone);\r\n");
    impl2.append("end;\r\n\r\n");

    prsrdefX.append("    Procedure ParseElementAttributes(value : TFhirElement; element : IXmlDomElement);\r\n");
    prsrImpl.append("Procedure TFHIRXmlParser.ParseElementAttributes(value : TFhirElement; element : IXmlDomElement);\r\n");
    prsrImpl.append("begin\r\n");
    prsrImpl.append("  TakeCommentsStart(value);\r\n");
    prsrImpl.append("  value.xmlId := GetAttribute(element, 'id');\r\n");
    prsrImpl.append("end;\r\n\r\n");

    prsrdefX.append("    Function ParseElementChild(element : TFhirElement; child : IXmlDomElement) : boolean;\r\n");
    prsrImpl.append("Function TFHIRXmlParser.ParseElementChild(element : TFhirElement; child : IXmlDomElement) : boolean;\r\n");
    prsrImpl.append("begin\r\n");
    prsrImpl.append("  result := true;\r\n");
    prsrImpl.append("  if (child.baseName = 'extension') then\r\n");
    prsrImpl.append("    element.ExtensionList.add(ParseExtension(child))\r\n");
    prsrImpl.append("  else\r\n");
    prsrImpl.append("    result := false;\r\n");
    prsrImpl.append("end;\r\n\r\n");
    
    prsrdefJ.append("    Function ParseElementProperty(element : TFhirElement) : boolean;\r\n");
    prsrImpl.append("Function TFHIRJsonParser.ParseElementProperty(element : TFhirElement) : boolean;\r\n");
    prsrImpl.append("begin\r\n");
    prsrImpl.append("  result := true;\r\n");
    prsrImpl.append("  if (json.ItemName = '_id') then\r\n");
    prsrImpl.append("    element.xmlId := json.itemValue\r\n");
    prsrImpl.append("  else if (json.ItemName = 'extension') then\r\n");
    prsrImpl.append("  begin\r\n");
    prsrImpl.append("    json.checkState(jpitArray);\r\n");
    prsrImpl.append("    json.Next;\r\n");
    prsrImpl.append("    while (json.ItemType <> jpitEnd) do\r\n");
    prsrImpl.append("    begin\r\n");
    prsrImpl.append("      element.extensionList.Add(ParseExtension);\r\n");
    prsrImpl.append("      json.Next;\r\n");
    prsrImpl.append("    end;\r\n");
    prsrImpl.append("  end\r\n");
    prsrImpl.append("  else\r\n");
    prsrImpl.append("    result := false;\r\n\r\n");
    prsrImpl.append("end;\r\n\r\n");

    srlsdefX.append("    Procedure ComposeElementAttributes(xml : TMsXmlBuilder; element : TFhirElement);\r\n");
    prsrImpl.append("Procedure TFHIRXmlComposer.ComposeElementAttributes(xml : TMsXmlBuilder; element : TFhirElement);\r\n");
    prsrImpl.append("begin\r\n");
    prsrImpl.append("  CommentsStart(xml, element);\r\n");
    prsrImpl.append("  Attribute(xml, 'id', element.xmlId);\r\n");
    prsrImpl.append("end;\r\n\r\n");
    srlsdefX.append("    Procedure ComposeElementChildren(xml : TMsXmlBuilder; element : TFhirElement);\r\n");
    prsrImpl.append("Procedure TFHIRXmlComposer.ComposeElementChildren(xml : TMsXmlBuilder; element : TFhirElement);\r\n");
    prsrImpl.append("var\r\n");
    prsrImpl.append("  i : integer;\r\n");
    prsrImpl.append("begin\r\n");
    prsrImpl.append("  if element.hasExtensions then\r\n");
    prsrImpl.append("    for i := 0 to element.extensionList.count - 1 do\r\n");
    prsrImpl.append("       ComposeExtension(xml, 'extension', element.extensionList[i]);\r\n");
    prsrImpl.append("end;\r\n\r\n");
    srlsdefJ.append("    Procedure ComposeElementProperties(json : TJSONWriter; elem : TFhirElement);\r\n");
    prsrImpl.append("Procedure TFHIRJsonComposer.ComposeElementProperties(json : TJSONWriter; elem : TFhirElement);\r\n");
    prsrImpl.append("var\r\n");
    prsrImpl.append("  i : integer;\r\n");
    prsrImpl.append("begin\r\n");
    prsrImpl.append("  Prop(json, '_id', elem.xmlId);\r\n");
    prsrImpl.append("  if elem.hasExtensions then\r\n");
    prsrImpl.append("  begin\r\n");
    prsrImpl.append("    json.valueArray('extension');\r\n");
    prsrImpl.append("    for i := 0 to elem.extensionList.count - 1 do\r\n");
    prsrImpl.append("       ComposeExtension(json, '', elem.extensionList[i]);\r\n");
    prsrImpl.append("    json.FinishArray;\r\n");
    prsrImpl.append("  end;\r\n");
    prsrImpl.append("end;\r\n\r\n");
        
    defCodeType.classDefs.add(def.toString());
    defCodeType.classImpls.add(impl2.toString());
    defCodeType.classFwds.add("  TFhirElement = class;\r\n");
    defineList("TFhirElement", "TFhirElementList", ClassCategory.Type, true);

   
  }

  private void generateResource() throws Exception {
    String prefix = "frt";
    StringBuilder def = new StringBuilder();
    StringBuilder con = new StringBuilder();
    def.append("  {@Enum TFhirResourceType\r\n");
    def.append("    Enumeration of known resource types\r\n");
    def.append("  }\r\n");
    def.append("  TFhirResourceType = (\r\n");
    con.append("  CODES_TFhirResourceType : Array[TFhirResourceType] of String = (");
    def.append("    frtNull, {@enum.value Resource type not known / not Specified }\r\n");
    con.append("'', ");

    List<String> types = new ArrayList<String>();
    for (String s : definitions.getResources().keySet()) 
      types.add(s);
    Collections.sort(types);
    
    for (String s : types)  {
      String s2 = prefix + getTitle(s);
      if (GeneratorUtils.isDelphiReservedWord(s2))
        s2 = s2 + "_";
      def.append("    "+s2+", {@enum.value "+Utilities.normaliseEolns(definitions.getResourceByName(s).getDefinition())+" }\r\n");
      con.append("'"+s+"', ");
    }
    def.append("    "+prefix+"Binary); {@enum.value Binary Resource }\r\n");
    con.append("'Binary');");

    def.append("\r\n  TFhirResourceTypeSet = set of TFhirResourceType;");
    con.append("\r\n  PLURAL_CODES_TFhirResourceType : Array[TFhirResourceType] of String = (");
    con.append("'', ");
    for (String s : types) {
      con.append("'"+Utilities.pluralizeMe(s.toLowerCase())+"', ");
    }
    con.append("'binaries');");
    con.append("\r\n  LOWERCASE_CODES_TFhirResourceType : Array[TFhirResourceType] of String = (");
    con.append("'', ");
    for (String s : types) {
        con.append("'"+s.toLowerCase()+"', ");
    }
  con.append("'binary');");

    con.append("\r\n  CLASSES_TFhirResourceType : Array[TFhirResourceType] of TFhirResourceClass = (");
    con.append("nil, ");
    for (String s : types) {
      con.append("TFhir"+getTitle(s)+", ");
    }
    con.append("TFhirBinary);");

    con.append("\r\n  ALL_RESOURCE_TYPES = [");
    for (String s : types)  {
      String s2 = prefix + getTitle(s);
      if (GeneratorUtils.isDelphiReservedWord(s2))
        s2 = s2 + "_";
      con.append(s2+", ");
    }
    con.append("frtBinary];\r\n");

    
    defCodeRes.enumDefs.add(def.toString());
    defCodeRes.enumConsts.add(con.toString());

       

    def = new StringBuilder();
    

    def.append("  {@Class TFhirResource : TFhirElement\r\n");
    def.append("    Base Resource Definition - extensions, narrative, contained resources\r\n");
    def.append("  }\r\n");
    def.append("  {!.Net HL7Connect.Fhir.Resource}\r\n");
    def.append("  TFhirResource = {abstract} class (TFhirElement)\r\n");
    def.append("  private\r\n");
    def.append("    FText : TFhirNarrative;\r\n");
    def.append("    FLanguage : TFhirCode;\r\n");
    def.append("    FFormat : TFHIRFormat;\r\n");
    def.append("    FContainedList : TFhirResourceList;\r\n");
    def.append("    procedure SetText(value : TFhirNarrative);\r\n");
    def.append("    procedure SetLanguage(value : TFhirCode);\r\n");
    def.append("  protected\r\n");
    def.append("    function GetResourceType : TFhirResourceType; virtual; abstract;\r\n");
    def.append("  protected\r\n");
    def.append("    Procedure GetChildrenByName(child_name : string; list : TFHIRObjectList); override;\r\n");
    def.append("    Procedure ListProperties(oList : TFHIRPropertyList; bInheritedProperties : Boolean); Override;\r\n");
    def.append("  public\r\n");
    def.append("    constructor Create; override;\r\n");
    def.append("    destructor Destroy; override;\r\n");
    def.append("    {!script hide}\r\n");
    def.append("    procedure Assign(oSource : TAdvObject); override;\r\n");
    def.append("    function Link : TFhirResource; overload;\r\n");
    def.append("    function Clone : TFhirResource; overload;\r\n");
    def.append("    {!script show}\r\n");
    def.append("  published\r\n");
    def.append("    Property ResourceType : TFhirResourceType read GetResourceType;\r\n\r\n");
    def.append("    {@member language\r\n");
    def.append("      The base language of the resource\r\n");
    def.append("    }\r\n");
    def.append("    property language : TFhirCode read FLanguage write SetLanguage;\r\n");
    def.append("    {@member text\r\n");
    def.append("      Text summary of resource, for human interpretation\r\n");
    def.append("    }\r\n");
    def.append("    property text : TFhirNarrative read FText write SetText;\r\n");
    def.append("    {@member containedList\r\n");
    def.append("      Text summary of resource, for human interpretation\r\n");
    def.append("    }\r\n");
    def.append("    property containedList : TFhirResourceList read FContainedList;\r\n");
    def.append("    {@member _source_format\r\n");
    def.append("      Whether the resource was first represented in XML or JSON\r\n");
    def.append("    }\r\n");
    def.append("    property _source_format : TFHIRFormat read FFormat write FFormat;\r\n");
    def.append("  end;\r\n");
    def.append("  \r\n");
    def.append("  TFhirResourceClass = class of TFhirResource;\r\n");
    def.append("  \r\n");
    def.append("  \r\n");
    def.append("  {@Class TFhirBinary : TFhirResource\r\n");
    def.append("    Special Binary Resource\r\n");
    def.append("  }\r\n");
    def.append("  {!.Net HL7Connect.Fhir.Binary}\r\n");
    def.append("  TFhirBinary = class (TFhirResource)\r\n");
    def.append("  private\r\n");
    def.append("    FContent : TAdvBuffer;\r\n");
    def.append("    FContentType : string;\r\n");
    def.append("  protected\r\n");
    def.append("    function GetResourceType : TFhirResourceType; override;\r\n");
    def.append("  public\r\n");
    def.append("    Constructor create; Overload; Override;\r\n");
    def.append("    Destructor Destroy; Override;\r\n");
    def.append("  published\r\n");
    def.append("    Property Content : TAdvBuffer read FContent;\r\n");
    def.append("    Property ContentType : string read FContentType write FContentType;\r\n");
    def.append("  end;\r\n");
    def.append("  \r\n");
    
    def.append("\r\n");
    StringBuilder impl2 = new StringBuilder();
    impl2.append("{ TFhirResource }\r\n\r\n");
    impl2.append("constructor TFhirResource.Create;\r\n");
    impl2.append("begin\r\n");
    impl2.append("  FContainedList := TFhirResourceList.create;\r\n");
    impl2.append("  inherited;\r\n");
    impl2.append("end;\r\n\r\n");

    impl2.append("destructor TFhirResource.Destroy;\r\n");
    impl2.append("begin\r\n");
    impl2.append("  FText.Free;\r\n");
    impl2.append("  FContainedList.Free;\r\n");
    impl2.append("  inherited;\r\n");
    impl2.append("end;\r\n\r\n");
    
    impl2.append("procedure TFhirResource.GetChildrenByName(child_name : string; list : TFHIRObjectList);\r\n");
    impl2.append("begin\r\n");
    impl2.append("  inherited;\r\n");
    impl2.append("  if (child_name = 'contained') then\r\n    list.addAll(FContainedList);\r\n");
    impl2.append("  if (child_name = 'text') then\r\n    list.add(text.Link);\r\n");
    impl2.append("end;\r\n\r\n");
    impl2.append("procedure TFhirResource.ListProperties(oList: TFHIRPropertyList; bInheritedProperties: Boolean);\r\n");
    impl2.append("begin\r\n");
    impl2.append("  inherited;\r\n");
    impl2.append("  oList.add(TFHIRProperty.create(self, 'contained', 'Resource', FContainedList.Link));\r\n");
    impl2.append("  oList.add(TFHIRProperty.create(self, 'text', 'Narrative', FText.Link));\r\n");
    impl2.append("end;\r\n\r\n");
    
    
    impl2.append("procedure TFhirResource.Assign(oSource : TAdvObject);\r\n");
    impl2.append("begin\r\n");
    impl2.append("  inherited;\r\n");
    impl2.append("  FFormat := TFhirResource(oSource).FFormat;\r\n");
    impl2.append("  containedList.assign(TFhirResource(oSource).containedList);\r\n");
    impl2.append("  text := TFhirResource(oSource).text.Clone;\r\n");
    impl2.append("end;\r\n\r\n");
    
    impl2.append("function TFhirResource.Link : TFhirResource;\r\n");
    impl2.append("begin\r\n");
    impl2.append("  result := TFhirResource(inherited Link);\r\n");
    impl2.append("end;\r\n\r\n");
    impl2.append("function TFhirResource.Clone : TFhirResource;\r\n");
    impl2.append("begin\r\n");
    impl2.append("  result := TFhirResource(inherited Clone);\r\n");
    impl2.append("end;\r\n\r\n");
    impl2.append("procedure TFhirResource.SetText(value : TFhirNarrative);\r\n");
    impl2.append("begin\r\n");
    impl2.append("  FText.Free;\r\n");
    impl2.append("  FText := value;\r\n");
    impl2.append("end;\r\n\r\n");
    impl2.append("procedure TFhirResource.SetLanguage(value : TFhirCode);\r\n");
    impl2.append("begin\r\n");
    impl2.append("  FLanguage.Free;\r\n");
    impl2.append("  FLanguage := value;\r\n");
    impl2.append("end;\r\n\r\n");


    impl2.append("constructor TFhirBinary.create;\r\n");
    impl2.append("begin\r\n");
    impl2.append("  inherited;\r\n");
    impl2.append("  FContent := TAdvBuffer.create;\r\n");
    impl2.append("end;\r\n");
    impl2.append("\r\n");
    impl2.append("destructor TFhirBinary.Destroy;\r\n");
    impl2.append("begin\r\n");
    impl2.append("  FContent.free;\r\n");
    impl2.append("  inherited;\r\n");
    impl2.append("end;\r\n");
    impl2.append("\r\n");    
    impl2.append("function TFhirBinary.GetResourceType : TFhirResourceType;\r\n");
    impl2.append("begin\r\n");
    impl2.append("  result := frtBinary;\r\n");
    impl2.append("end;\r\n");
    impl2.append("\r\n");    
    defCodeRes.classDefs.add(def.toString());
    defCodeRes.classImpls.add(impl2.toString());
    defCodeRes.classFwds.add("  TFhirResource = class;\r\n");
    defineList("TFhirResource", "TFhirResourceList", ClassCategory.Resource, true);
    
    prsrdefX.append("    Procedure ParseResourceAttributes(resource : TFhirResource; element : IXmlDomElement);\r\n");
    prsrImpl.append("Procedure TFHIRXmlParser.ParseResourceAttributes(resource : TFhirResource; element : IXmlDomElement);\r\n");
    prsrImpl.append("begin\r\n");
    prsrImpl.append("  ParseElementAttributes(resource, element);\r\n");
    prsrImpl.append("  // lang\r\n");
    prsrImpl.append("end;\r\n\r\n");
    prsrdefX.append("    Function ParseResourceChild(resource : TFhirResource; child : IXmlDomElement) : boolean;\r\n");
    prsrImpl.append("Function TFHIRXmlParser.ParseResourceChild(resource : TFhirResource; child : IXmlDomElement) : boolean;\r\n");
    prsrImpl.append("begin\r\n");
    prsrImpl.append("  result := true;\r\n");
    prsrImpl.append("  if (child.baseName = 'text') then\r\n");
    prsrImpl.append("    resource.text := ParseNarrative(child)\r\n");
    prsrImpl.append("  else if (child.baseName = 'language') then\r\n");
    prsrImpl.append("    resource.language := ParseCode(child)\r\n");
    prsrImpl.append("  else if (child.baseName = 'contained') then\r\n");
    prsrImpl.append("    resource.ContainedList.add(ParseContained(child))\r\n");
    prsrImpl.append("  else if not parseElementChild(resource, child) then\r\n");
    prsrImpl.append("    result := false;\r\n");
    prsrImpl.append("end;\r\n\r\n");
    
    prsrdefJ.append("    Function ParseResourceProperty(resource : TFhirResource) : boolean;\r\n");
    prsrImpl.append("Function TFHIRJsonParser.ParseResourceProperty(resource : TFhirResource) : boolean;\r\n");
    prsrImpl.append("begin\r\n");
    prsrImpl.append("  result := true;\r\n");
    prsrImpl.append("  if (json.ItemName = 'language') then\r\n");
    prsrImpl.append("    resource.language := parseCode\r\n");
    prsrImpl.append("  else if (json.ItemName = 'text') then\r\n");
    prsrImpl.append("    resource.text := parseNarrative\r\n");
    prsrImpl.append("  else if (json.ItemName = 'contained') then\r\n");
    prsrImpl.append("  begin\r\n");
    prsrImpl.append("    json.checkState(jpitArray);\r\n");
    prsrImpl.append("    json.Next;\r\n");
    prsrImpl.append("    while (json.ItemType <> jpitEnd) do\r\n");
    prsrImpl.append("    begin\r\n");
    prsrImpl.append("      resource.containedList.Add(ParseContained);\r\n");
    prsrImpl.append("      json.Next;\r\n");
    prsrImpl.append("    end;\r\n");
    prsrImpl.append("  end\r\n");
    prsrImpl.append("  else if not ParseElementProperty(resource) then\r\n");
    prsrImpl.append("    result := false;\r\n\r\n");
    prsrImpl.append("end;\r\n\r\n");


    srlsdefX.append("    Procedure ComposeResourceAttributes(xml : TMsXmlBuilder; resource : TFhirResource);\r\n");
    prsrImpl.append("Procedure TFHIRXmlComposer.ComposeResourceAttributes(xml : TMsXmlBuilder; resource : TFhirResource);\r\n");
    prsrImpl.append("begin\r\n");
    prsrImpl.append("  ComposeElementAttributes(xml, resource);\r\n");
    prsrImpl.append("end;\r\n\r\n");
    srlsdefX.append("    Procedure ComposeResourceChildren(xml : TMsXmlBuilder; resource : TFhirResource);\r\n");
    prsrImpl.append("Procedure TFHIRXmlComposer.ComposeResourceChildren(xml : TMsXmlBuilder; resource : TFhirResource);\r\n");
    prsrImpl.append("var\r\n");
    prsrImpl.append("  i : integer;\r\n");
    prsrImpl.append("begin\r\n");
    prsrImpl.append("  composeElementChildren(xml, resource);\r\n");
    prsrImpl.append("  composeCode(xml, 'language', resource.language);\r\n");
    prsrImpl.append("  composeNarrative(xml, 'text', resource.text);\r\n");    
    prsrImpl.append("  for i := 0 to resource.containedList.count - 1 do\r\n");
    prsrImpl.append("    ComposeContained(xml, 'contained', resource.containedList[i]);\r\n");
    prsrImpl.append("end;\r\n\r\n");
    srlsdefJ.append("    Procedure ComposeResourceProperties(json : TJSONWriter; resource : TFhirResource);\r\n");
    prsrImpl.append("Procedure TFHIRJsonComposer.ComposeResourceProperties(json : TJSONWriter; resource : TFhirResource);\r\n");
    prsrImpl.append("var\r\n");
    prsrImpl.append("  i : integer;\r\n");
    prsrImpl.append("begin\r\n");
    prsrImpl.append("  ComposeElementProperties(json, resource);\r\n");
    prsrImpl.append("  composeCode(json, 'language', resource.language);\r\n");
    prsrImpl.append("  ComposeNarrative(json, 'text', resource.text);\r\n");
    prsrImpl.append("  if resource.containedList.count > 0 then\r\n");
    prsrImpl.append("  begin\r\n");
    prsrImpl.append("    json.valueArray('contained');\r\n");
    prsrImpl.append("    for i := 0 to resource.containedList.Count - 1 do\r\n");
    prsrImpl.append("      ComposeContained(json, resource.containedList[i]);\r\n");
    prsrImpl.append("    json.FinishArray;\r\n");
    prsrImpl.append("  end;\r\n");
    prsrImpl.append("end;\r\n\r\n");
        
  }

  
  private void initParser(String version, Date genDate) {
    prsrCode.uses.add("SysUtils");
    prsrCode.uses.add("Classes");
    prsrCode.uses.add("ActiveX");
    prsrCode.uses.add("StringSupport");
    prsrCode.uses.add("DateSupport");
    prsrCode.uses.add("IdSoapMsXml");
    prsrCode.uses.add("FHIRParserBase");
    prsrCode.uses.add("MSSEWrap");
    prsrCode.uses.add("FHIRBase");
    prsrCode.uses.add("FHIRResources");
    prsrCode.uses.add("FHIRComponents");
    prsrCode.uses.add("FHIRTypes");
    prsrCode.uses.add("MsXmlParser");
    prsrCode.uses.add("MsXmlBuilder");
    prsrCode.uses.add("JSON");
    prsrCode.comments.add("FHIR v"+version+" generated "+Config.DATE_FORMAT().format(genDate));
    
    prsrImpl.append(
        "{ TFHIRXmlParser }\r\n"+
        "\r\n"
        );
    
  }

  private String buildParser() {
    
    prsrImpl.append(
        "function TFHIRXmlParser.ParseResource(element : IXmlDomElement) : TFhirResource;\r\n"+
        "begin\r\n"+
        "  if (element = nil) Then\r\n"+
        "    Raise Exception.Create('error - element is nil')\r\n"+
        prsrRegX.toString()+
        "  else if (element.baseName = 'Binary') Then\r\n"+
        "    result := ParseBinary(element)\r\n"+
        "  else\r\n"+
        "    raise Exception.create('Error: the element '+element.baseName+' is not recognised as a valid resource name');\r\n" +
        "end;\r\n\r\n"
        );

    prsrImpl.append(
        "procedure TFHIRXmlComposer.ComposeResource(xml : TMsXmlBuilder; id, ver : String; resource: TFhirResource);\r\n"+
        "begin\r\n"+
        "  if (resource = nil) Then\r\n"+
        "    Raise Exception.Create('error - resource is nil');\r\n"+
        "  Case resource.ResourceType of\r\n"+
        srlsRegX.toString()+
        "    frtBinary: ComposeBinary(xml, TFhirBinary(resource));\r\n"+
        "  else\r\n"+
        "    raise Exception.create('Internal error: the resource type '+CODES_TFhirResourceType[resource.ResourceType]+' is not a valid resource type');\r\n" +
        "  end;\r\n"+
        "end;\r\n\r\n"
        );

    prsrImpl.append(
        "function TFHIRJsonParser.ParseResource : TFhirResource;\r\n"+
        "begin\r\n "+
        prsrRegJ.toString().substring(6)+
        "  else if (json.itemName = 'Binary') Then\r\n"+
        "    result := ParseBinary\r\n"+
        "  else\r\n"+
        "    raise Exception.create('error: the element '+json.itemName+' is not a valid resource name');\r\n" +
        "end;\r\n\r\n"
        );

    prsrImpl.append(
        "procedure TFHIRJsonComposer.ComposeResource(json : TJSONWriter; id, ver : String; resource: TFhirResource);\r\n"+
        "begin\r\n"+
        "  if (resource = nil) Then\r\n"+
        "    Raise Exception.Create('error - resource is nil');\r\n"+
        "  Case resource.ResourceType of\r\n"+
        srlsRegJ.toString()+
        "    frtBinary: ComposeBinary(json, TFhirBinary(resource));\r\n"+
        "  else\r\n"+
        "    raise Exception.create('Internal error: the resource type '+CODES_TFhirResourceType[resource.ResourceType]+' is not a valid resource type');\r\n" +
        "  end;\r\n"+
        "end;\r\n\r\n"
        );

    return
        "  TFHIRXmlParser = class (TFHIRXmlParserBase)\r\n"+
        "  protected\r\n"+
        prsrdefX.toString()+
        "    function ParseResource(element : IxmlDomElement) : TFhirResource; override;\r\n"+
        "  end;\r\n\r\n"+
        "  TFHIRXmlComposer = class (TFHIRXmlComposerBase)\r\n"+
        "  protected\r\n"+
        srlsdefX.toString()+
        "    procedure ComposeResource(xml : TMsXmlBuilder; id, ver : String; resource : TFhirResource); override;\r\n"+
        "  end;\r\n\r\n"+
        "  TFHIRJsonParser = class (TFHIRJsonParserBase)\r\n"+
        "  protected\r\n"+
        prsrdefJ.toString()+
        "    function ParseResource : TFhirResource; override;\r\n"+
        "  end;\r\n\r\n"+
        "  TFHIRJsonComposer = class (TFHIRJsonComposerBase)\r\n"+
        "  protected\r\n"+
        srlsdefJ.toString()+
        "    procedure ComposeResource(json : TJSONWriter; id, ver : String; resource : TFhirResource); override;\r\n"+
        "  end;\r\n\r\n";
  }

  @Override
public String getDescription() {
    return "Resource Definitions and XML & JSON parsers. D5+. TODO: remove dependencies on unpublished code.";
  }

  @Override
public String getTitle() {
    return "Delphi";
  }

  @Override
public boolean isECoreGenerator() {
    return false;
  }

  @Override
public void generate(org.hl7.fhir.definitions.ecore.fhir.Definitions definitions, String destDir,
      String implDir, Logger logger) throws Exception {

    throw new UnsupportedOperationException("Delphi generator uses ElementDefn-style definitions.");
  }

  @Override
public boolean doesCompile() {
    return false;
  }

  @Override
public boolean compile(String rootDir, List<String> errors) {
    return false;
  }

  @Override
public boolean doesTest() {
    return false;
  }

  @Override
public void loadAndSave(String rootDir, String sourceFile, String destFile) {
    
  }

  @Override
public String checkFragments(String rootDir, String fragments) throws Exception {
    return "Not supported by delphi implementation";
  }

}
