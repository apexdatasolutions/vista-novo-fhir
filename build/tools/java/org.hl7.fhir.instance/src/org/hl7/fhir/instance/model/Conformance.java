package org.hl7.fhir.instance.model;

/*
  Copyright (c) 2011-2013, HL7, Inc.
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

// Generated on Sun, Dec 1, 2013 22:52+1100 for FHIR v0.12

import java.util.*;

/**
 * A conformance statement about how an application or implementation supports FHIR or the set of requirements for a desired implementation.
 */
public class Conformance extends Resource {

    public enum ConformanceStatementStatus {
        draft, // This conformance statement is still under development.
        active, // This conformance statement is ready for use in production systems.
        retired, // This conformance statement has been withdrawn or superceded and should no longer be used.
        Null; // added to help the parsers
        public static ConformanceStatementStatus fromCode(String codeString) throws Exception {
            if (codeString == null || "".equals(codeString))
                return null;
        if ("draft".equals(codeString))
          return draft;
        if ("active".equals(codeString))
          return active;
        if ("retired".equals(codeString))
          return retired;
        throw new Exception("Unknown ConformanceStatementStatus code '"+codeString+"'");
        }
        public String toCode() {
          switch (this) {
            case draft: return "draft";
            case active: return "active";
            case retired: return "retired";
            default: return "?";
          }
        }
    }

  public static class ConformanceStatementStatusEnumFactory implements EnumFactory {
    public Enum<?> fromCode(String codeString) throws Exception {
      if (codeString == null || "".equals(codeString))
            if (codeString == null || "".equals(codeString))
                return null;
        if ("draft".equals(codeString))
          return ConformanceStatementStatus.draft;
        if ("active".equals(codeString))
          return ConformanceStatementStatus.active;
        if ("retired".equals(codeString))
          return ConformanceStatementStatus.retired;
        throw new Exception("Unknown ConformanceStatementStatus code '"+codeString+"'");
        }
    public String toCode(Enum<?> code) throws Exception {
      if (code == ConformanceStatementStatus.draft)
        return "draft";
      if (code == ConformanceStatementStatus.active)
        return "active";
      if (code == ConformanceStatementStatus.retired)
        return "retired";
      return "?";
      }
    }

    public enum RestfulConformanceMode {
        client, // The application acts as a server for this resource.
        server, // The application acts as a client for this resource.
        Null; // added to help the parsers
        public static RestfulConformanceMode fromCode(String codeString) throws Exception {
            if (codeString == null || "".equals(codeString))
                return null;
        if ("client".equals(codeString))
          return client;
        if ("server".equals(codeString))
          return server;
        throw new Exception("Unknown RestfulConformanceMode code '"+codeString+"'");
        }
        public String toCode() {
          switch (this) {
            case client: return "client";
            case server: return "server";
            default: return "?";
          }
        }
    }

  public static class RestfulConformanceModeEnumFactory implements EnumFactory {
    public Enum<?> fromCode(String codeString) throws Exception {
      if (codeString == null || "".equals(codeString))
            if (codeString == null || "".equals(codeString))
                return null;
        if ("client".equals(codeString))
          return RestfulConformanceMode.client;
        if ("server".equals(codeString))
          return RestfulConformanceMode.server;
        throw new Exception("Unknown RestfulConformanceMode code '"+codeString+"'");
        }
    public String toCode(Enum<?> code) throws Exception {
      if (code == RestfulConformanceMode.client)
        return "client";
      if (code == RestfulConformanceMode.server)
        return "server";
      return "?";
      }
    }

    public enum TypeRestfulOperation {
        read, // 
        vread, // 
        update, // 
        delete, // 
        historyinstance, // 
        validate, // 
        historytype, // 
        create, // 
        searchtype, // 
        Null; // added to help the parsers
        public static TypeRestfulOperation fromCode(String codeString) throws Exception {
            if (codeString == null || "".equals(codeString))
                return null;
        if ("read".equals(codeString))
          return read;
        if ("vread".equals(codeString))
          return vread;
        if ("update".equals(codeString))
          return update;
        if ("delete".equals(codeString))
          return delete;
        if ("history-instance".equals(codeString))
          return historyinstance;
        if ("validate".equals(codeString))
          return validate;
        if ("history-type".equals(codeString))
          return historytype;
        if ("create".equals(codeString))
          return create;
        if ("search-type".equals(codeString))
          return searchtype;
        throw new Exception("Unknown TypeRestfulOperation code '"+codeString+"'");
        }
        public String toCode() {
          switch (this) {
            case read: return "read";
            case vread: return "vread";
            case update: return "update";
            case delete: return "delete";
            case historyinstance: return "history-instance";
            case validate: return "validate";
            case historytype: return "history-type";
            case create: return "create";
            case searchtype: return "search-type";
            default: return "?";
          }
        }
    }

  public static class TypeRestfulOperationEnumFactory implements EnumFactory {
    public Enum<?> fromCode(String codeString) throws Exception {
      if (codeString == null || "".equals(codeString))
            if (codeString == null || "".equals(codeString))
                return null;
        if ("read".equals(codeString))
          return TypeRestfulOperation.read;
        if ("vread".equals(codeString))
          return TypeRestfulOperation.vread;
        if ("update".equals(codeString))
          return TypeRestfulOperation.update;
        if ("delete".equals(codeString))
          return TypeRestfulOperation.delete;
        if ("history-instance".equals(codeString))
          return TypeRestfulOperation.historyinstance;
        if ("validate".equals(codeString))
          return TypeRestfulOperation.validate;
        if ("history-type".equals(codeString))
          return TypeRestfulOperation.historytype;
        if ("create".equals(codeString))
          return TypeRestfulOperation.create;
        if ("search-type".equals(codeString))
          return TypeRestfulOperation.searchtype;
        throw new Exception("Unknown TypeRestfulOperation code '"+codeString+"'");
        }
    public String toCode(Enum<?> code) throws Exception {
      if (code == TypeRestfulOperation.read)
        return "read";
      if (code == TypeRestfulOperation.vread)
        return "vread";
      if (code == TypeRestfulOperation.update)
        return "update";
      if (code == TypeRestfulOperation.delete)
        return "delete";
      if (code == TypeRestfulOperation.historyinstance)
        return "history-instance";
      if (code == TypeRestfulOperation.validate)
        return "validate";
      if (code == TypeRestfulOperation.historytype)
        return "history-type";
      if (code == TypeRestfulOperation.create)
        return "create";
      if (code == TypeRestfulOperation.searchtype)
        return "search-type";
      return "?";
      }
    }

    public enum SearchParamType {
        number, // Search parameter SHALL be a number (an whole number, or a decimal).
        date, // Search parameter is on a date/time. The date format is the standard XML format, though other formats may be supported.
        string, // Search parameter is a simple string, like a name part. Search is case-insensitive and accent-insensitive. May match just the start of a string. String parameters may contain spaces.
        token, // Search parameter on a coded element or identifier. May be used to search through the text, displayname, code and code/codesystem (for codes) and label, system and key (for identifier). Its value is either a string or a pair of namespace and value, separated by a "|", depending on the modifier used.
        reference, // A reference to another resource.
        composite, // A composite search parameter that combines a search on two values together.
        Null; // added to help the parsers
        public static SearchParamType fromCode(String codeString) throws Exception {
            if (codeString == null || "".equals(codeString))
                return null;
        if ("number".equals(codeString))
          return number;
        if ("date".equals(codeString))
          return date;
        if ("string".equals(codeString))
          return string;
        if ("token".equals(codeString))
          return token;
        if ("reference".equals(codeString))
          return reference;
        if ("composite".equals(codeString))
          return composite;
        throw new Exception("Unknown SearchParamType code '"+codeString+"'");
        }
        public String toCode() {
          switch (this) {
            case number: return "number";
            case date: return "date";
            case string: return "string";
            case token: return "token";
            case reference: return "reference";
            case composite: return "composite";
            default: return "?";
          }
        }
    }

  public static class SearchParamTypeEnumFactory implements EnumFactory {
    public Enum<?> fromCode(String codeString) throws Exception {
      if (codeString == null || "".equals(codeString))
            if (codeString == null || "".equals(codeString))
                return null;
        if ("number".equals(codeString))
          return SearchParamType.number;
        if ("date".equals(codeString))
          return SearchParamType.date;
        if ("string".equals(codeString))
          return SearchParamType.string;
        if ("token".equals(codeString))
          return SearchParamType.token;
        if ("reference".equals(codeString))
          return SearchParamType.reference;
        if ("composite".equals(codeString))
          return SearchParamType.composite;
        throw new Exception("Unknown SearchParamType code '"+codeString+"'");
        }
    public String toCode(Enum<?> code) throws Exception {
      if (code == SearchParamType.number)
        return "number";
      if (code == SearchParamType.date)
        return "date";
      if (code == SearchParamType.string)
        return "string";
      if (code == SearchParamType.token)
        return "token";
      if (code == SearchParamType.reference)
        return "reference";
      if (code == SearchParamType.composite)
        return "composite";
      return "?";
      }
    }

    public enum SystemRestfulOperation {
        transaction, // 
        searchsystem, // 
        historysystem, // 
        Null; // added to help the parsers
        public static SystemRestfulOperation fromCode(String codeString) throws Exception {
            if (codeString == null || "".equals(codeString))
                return null;
        if ("transaction".equals(codeString))
          return transaction;
        if ("search-system".equals(codeString))
          return searchsystem;
        if ("history-system".equals(codeString))
          return historysystem;
        throw new Exception("Unknown SystemRestfulOperation code '"+codeString+"'");
        }
        public String toCode() {
          switch (this) {
            case transaction: return "transaction";
            case searchsystem: return "search-system";
            case historysystem: return "history-system";
            default: return "?";
          }
        }
    }

  public static class SystemRestfulOperationEnumFactory implements EnumFactory {
    public Enum<?> fromCode(String codeString) throws Exception {
      if (codeString == null || "".equals(codeString))
            if (codeString == null || "".equals(codeString))
                return null;
        if ("transaction".equals(codeString))
          return SystemRestfulOperation.transaction;
        if ("search-system".equals(codeString))
          return SystemRestfulOperation.searchsystem;
        if ("history-system".equals(codeString))
          return SystemRestfulOperation.historysystem;
        throw new Exception("Unknown SystemRestfulOperation code '"+codeString+"'");
        }
    public String toCode(Enum<?> code) throws Exception {
      if (code == SystemRestfulOperation.transaction)
        return "transaction";
      if (code == SystemRestfulOperation.searchsystem)
        return "search-system";
      if (code == SystemRestfulOperation.historysystem)
        return "history-system";
      return "?";
      }
    }

    public enum MessageConformanceEventMode {
        sender, // The application sends requests and receives responses.
        receiver, // The application receives requests and sends responses.
        Null; // added to help the parsers
        public static MessageConformanceEventMode fromCode(String codeString) throws Exception {
            if (codeString == null || "".equals(codeString))
                return null;
        if ("sender".equals(codeString))
          return sender;
        if ("receiver".equals(codeString))
          return receiver;
        throw new Exception("Unknown MessageConformanceEventMode code '"+codeString+"'");
        }
        public String toCode() {
          switch (this) {
            case sender: return "sender";
            case receiver: return "receiver";
            default: return "?";
          }
        }
    }

  public static class MessageConformanceEventModeEnumFactory implements EnumFactory {
    public Enum<?> fromCode(String codeString) throws Exception {
      if (codeString == null || "".equals(codeString))
            if (codeString == null || "".equals(codeString))
                return null;
        if ("sender".equals(codeString))
          return MessageConformanceEventMode.sender;
        if ("receiver".equals(codeString))
          return MessageConformanceEventMode.receiver;
        throw new Exception("Unknown MessageConformanceEventMode code '"+codeString+"'");
        }
    public String toCode(Enum<?> code) throws Exception {
      if (code == MessageConformanceEventMode.sender)
        return "sender";
      if (code == MessageConformanceEventMode.receiver)
        return "receiver";
      return "?";
      }
    }

    public enum DocumentMode {
        producer, // The application produces documents of the specified type.
        consumer, // The application consumes documents of the specified type.
        Null; // added to help the parsers
        public static DocumentMode fromCode(String codeString) throws Exception {
            if (codeString == null || "".equals(codeString))
                return null;
        if ("producer".equals(codeString))
          return producer;
        if ("consumer".equals(codeString))
          return consumer;
        throw new Exception("Unknown DocumentMode code '"+codeString+"'");
        }
        public String toCode() {
          switch (this) {
            case producer: return "producer";
            case consumer: return "consumer";
            default: return "?";
          }
        }
    }

  public static class DocumentModeEnumFactory implements EnumFactory {
    public Enum<?> fromCode(String codeString) throws Exception {
      if (codeString == null || "".equals(codeString))
            if (codeString == null || "".equals(codeString))
                return null;
        if ("producer".equals(codeString))
          return DocumentMode.producer;
        if ("consumer".equals(codeString))
          return DocumentMode.consumer;
        throw new Exception("Unknown DocumentMode code '"+codeString+"'");
        }
    public String toCode(Enum<?> code) throws Exception {
      if (code == DocumentMode.producer)
        return "producer";
      if (code == DocumentMode.consumer)
        return "consumer";
      return "?";
      }
    }

    public static class ConformanceSoftwareComponent extends BackboneElement {
        /**
         * Name software is known by.
         */
        protected String_ name;

        /**
         * Version covered by this statement.
         */
        protected String_ version;

        /**
         * Date this version of the software released.
         */
        protected DateTime releaseDate;

      public ConformanceSoftwareComponent() {
        super();
      }

      public ConformanceSoftwareComponent(String_ name) {
        super();
        this.name = name;
      }

        /**
         * @return {@link #name} (Name software is known by.)
         */
        public String_ getName() { 
          return this.name;
        }

        /**
         * @param value {@link #name} (Name software is known by.)
         */
        public ConformanceSoftwareComponent setName(String_ value) { 
          this.name = value;
          return this;
        }

        /**
         * @return Name software is known by.
         */
        public String getNameSimple() { 
          return this.name == null ? null : this.name.getValue();
        }

        /**
         * @param value Name software is known by.
         */
        public ConformanceSoftwareComponent setNameSimple(String value) { 
            if (this.name == null)
              this.name = new String_();
            this.name.setValue(value);
          return this;
        }

        /**
         * @return {@link #version} (Version covered by this statement.)
         */
        public String_ getVersion() { 
          return this.version;
        }

        /**
         * @param value {@link #version} (Version covered by this statement.)
         */
        public ConformanceSoftwareComponent setVersion(String_ value) { 
          this.version = value;
          return this;
        }

        /**
         * @return Version covered by this statement.
         */
        public String getVersionSimple() { 
          return this.version == null ? null : this.version.getValue();
        }

        /**
         * @param value Version covered by this statement.
         */
        public ConformanceSoftwareComponent setVersionSimple(String value) { 
          if (value == null)
            this.version = null;
          else {
            if (this.version == null)
              this.version = new String_();
            this.version.setValue(value);
          }
          return this;
        }

        /**
         * @return {@link #releaseDate} (Date this version of the software released.)
         */
        public DateTime getReleaseDate() { 
          return this.releaseDate;
        }

        /**
         * @param value {@link #releaseDate} (Date this version of the software released.)
         */
        public ConformanceSoftwareComponent setReleaseDate(DateTime value) { 
          this.releaseDate = value;
          return this;
        }

        /**
         * @return Date this version of the software released.
         */
        public String getReleaseDateSimple() { 
          return this.releaseDate == null ? null : this.releaseDate.getValue();
        }

        /**
         * @param value Date this version of the software released.
         */
        public ConformanceSoftwareComponent setReleaseDateSimple(String value) { 
          if (value == null)
            this.releaseDate = null;
          else {
            if (this.releaseDate == null)
              this.releaseDate = new DateTime();
            this.releaseDate.setValue(value);
          }
          return this;
        }

        protected void listChildren(List<Property> childrenList) {
          super.listChildren(childrenList);
          childrenList.add(new Property("name", "string", "Name software is known by.", 0, java.lang.Integer.MAX_VALUE, name));
          childrenList.add(new Property("version", "string", "Version covered by this statement.", 0, java.lang.Integer.MAX_VALUE, version));
          childrenList.add(new Property("releaseDate", "dateTime", "Date this version of the software released.", 0, java.lang.Integer.MAX_VALUE, releaseDate));
        }

      public ConformanceSoftwareComponent copy(Conformance e) {
        ConformanceSoftwareComponent dst = new ConformanceSoftwareComponent();
        dst.name = name == null ? null : name.copy();
        dst.version = version == null ? null : version.copy();
        dst.releaseDate = releaseDate == null ? null : releaseDate.copy();
        return dst;
      }

  }

    public static class ConformanceImplementationComponent extends BackboneElement {
        /**
         * Information about the specific installation that this conformance statement relates to.
         */
        protected String_ description;

        /**
         * The base URL for the implementation.  This forms the base for REST interfaces as well as the mailbox and document interfaces.
         */
        protected Uri url;

      public ConformanceImplementationComponent() {
        super();
      }

      public ConformanceImplementationComponent(String_ description) {
        super();
        this.description = description;
      }

        /**
         * @return {@link #description} (Information about the specific installation that this conformance statement relates to.)
         */
        public String_ getDescription() { 
          return this.description;
        }

        /**
         * @param value {@link #description} (Information about the specific installation that this conformance statement relates to.)
         */
        public ConformanceImplementationComponent setDescription(String_ value) { 
          this.description = value;
          return this;
        }

        /**
         * @return Information about the specific installation that this conformance statement relates to.
         */
        public String getDescriptionSimple() { 
          return this.description == null ? null : this.description.getValue();
        }

        /**
         * @param value Information about the specific installation that this conformance statement relates to.
         */
        public ConformanceImplementationComponent setDescriptionSimple(String value) { 
            if (this.description == null)
              this.description = new String_();
            this.description.setValue(value);
          return this;
        }

        /**
         * @return {@link #url} (The base URL for the implementation.  This forms the base for REST interfaces as well as the mailbox and document interfaces.)
         */
        public Uri getUrl() { 
          return this.url;
        }

        /**
         * @param value {@link #url} (The base URL for the implementation.  This forms the base for REST interfaces as well as the mailbox and document interfaces.)
         */
        public ConformanceImplementationComponent setUrl(Uri value) { 
          this.url = value;
          return this;
        }

        /**
         * @return The base URL for the implementation.  This forms the base for REST interfaces as well as the mailbox and document interfaces.
         */
        public String getUrlSimple() { 
          return this.url == null ? null : this.url.getValue();
        }

        /**
         * @param value The base URL for the implementation.  This forms the base for REST interfaces as well as the mailbox and document interfaces.
         */
        public ConformanceImplementationComponent setUrlSimple(String value) { 
          if (value == null)
            this.url = null;
          else {
            if (this.url == null)
              this.url = new Uri();
            this.url.setValue(value);
          }
          return this;
        }

        protected void listChildren(List<Property> childrenList) {
          super.listChildren(childrenList);
          childrenList.add(new Property("description", "string", "Information about the specific installation that this conformance statement relates to.", 0, java.lang.Integer.MAX_VALUE, description));
          childrenList.add(new Property("url", "uri", "The base URL for the implementation.  This forms the base for REST interfaces as well as the mailbox and document interfaces.", 0, java.lang.Integer.MAX_VALUE, url));
        }

      public ConformanceImplementationComponent copy(Conformance e) {
        ConformanceImplementationComponent dst = new ConformanceImplementationComponent();
        dst.description = description == null ? null : description.copy();
        dst.url = url == null ? null : url.copy();
        return dst;
      }

  }

    public static class ConformanceRestComponent extends BackboneElement {
        /**
         * Identifies whether this portion of the statement is describing ability to initiate or receive restful operations.
         */
        protected Enumeration<RestfulConformanceMode> mode;

        /**
         * Provides documentation about the system's restful capabilities that apply across all applications, such as security.
         */
        protected String_ documentation;

        /**
         * Information about security of implementation.
         */
        protected ConformanceRestSecurityComponent security;

        /**
         * Identifies the restful capabilities of the solution for a specific resource type.
         */
        protected List<ConformanceRestResourceComponent> resource = new ArrayList<ConformanceRestResourceComponent>();

        /**
         * Identifies a restful operation supported by the solution.
         */
        protected List<ConformanceRestOperationComponent> operation = new ArrayList<ConformanceRestOperationComponent>();

        /**
         * Definition of a named query and its parameters and their meaning.
         */
        protected List<ConformanceRestQueryComponent> query = new ArrayList<ConformanceRestQueryComponent>();

        /**
         * A list of profiles that this server implements for accepting documents on mailbox. If the list is empty, then documents are not accepted. The base specification has the profile identifier "http://hl7.org/fhir/documents/mailbox". Other specifications can declare their own identifier for this use.
         */
        protected List<Uri> documentMailbox = new ArrayList<Uri>();

      public ConformanceRestComponent() {
        super();
      }

      public ConformanceRestComponent(Enumeration<RestfulConformanceMode> mode) {
        super();
        this.mode = mode;
      }

        /**
         * @return {@link #mode} (Identifies whether this portion of the statement is describing ability to initiate or receive restful operations.)
         */
        public Enumeration<RestfulConformanceMode> getMode() { 
          return this.mode;
        }

        /**
         * @param value {@link #mode} (Identifies whether this portion of the statement is describing ability to initiate or receive restful operations.)
         */
        public ConformanceRestComponent setMode(Enumeration<RestfulConformanceMode> value) { 
          this.mode = value;
          return this;
        }

        /**
         * @return Identifies whether this portion of the statement is describing ability to initiate or receive restful operations.
         */
        public RestfulConformanceMode getModeSimple() { 
          return this.mode == null ? null : this.mode.getValue();
        }

        /**
         * @param value Identifies whether this portion of the statement is describing ability to initiate or receive restful operations.
         */
        public ConformanceRestComponent setModeSimple(RestfulConformanceMode value) { 
            if (this.mode == null)
              this.mode = new Enumeration<RestfulConformanceMode>();
            this.mode.setValue(value);
          return this;
        }

        /**
         * @return {@link #documentation} (Provides documentation about the system's restful capabilities that apply across all applications, such as security.)
         */
        public String_ getDocumentation() { 
          return this.documentation;
        }

        /**
         * @param value {@link #documentation} (Provides documentation about the system's restful capabilities that apply across all applications, such as security.)
         */
        public ConformanceRestComponent setDocumentation(String_ value) { 
          this.documentation = value;
          return this;
        }

        /**
         * @return Provides documentation about the system's restful capabilities that apply across all applications, such as security.
         */
        public String getDocumentationSimple() { 
          return this.documentation == null ? null : this.documentation.getValue();
        }

        /**
         * @param value Provides documentation about the system's restful capabilities that apply across all applications, such as security.
         */
        public ConformanceRestComponent setDocumentationSimple(String value) { 
          if (value == null)
            this.documentation = null;
          else {
            if (this.documentation == null)
              this.documentation = new String_();
            this.documentation.setValue(value);
          }
          return this;
        }

        /**
         * @return {@link #security} (Information about security of implementation.)
         */
        public ConformanceRestSecurityComponent getSecurity() { 
          return this.security;
        }

        /**
         * @param value {@link #security} (Information about security of implementation.)
         */
        public ConformanceRestComponent setSecurity(ConformanceRestSecurityComponent value) { 
          this.security = value;
          return this;
        }

        /**
         * @return {@link #resource} (Identifies the restful capabilities of the solution for a specific resource type.)
         */
        public List<ConformanceRestResourceComponent> getResource() { 
          return this.resource;
        }

    // syntactic sugar
        /**
         * @return {@link #resource} (Identifies the restful capabilities of the solution for a specific resource type.)
         */
        public ConformanceRestResourceComponent addResource() { 
          ConformanceRestResourceComponent t = new ConformanceRestResourceComponent();
          this.resource.add(t);
          return t;
        }

        /**
         * @return {@link #operation} (Identifies a restful operation supported by the solution.)
         */
        public List<ConformanceRestOperationComponent> getOperation() { 
          return this.operation;
        }

    // syntactic sugar
        /**
         * @return {@link #operation} (Identifies a restful operation supported by the solution.)
         */
        public ConformanceRestOperationComponent addOperation() { 
          ConformanceRestOperationComponent t = new ConformanceRestOperationComponent();
          this.operation.add(t);
          return t;
        }

        /**
         * @return {@link #query} (Definition of a named query and its parameters and their meaning.)
         */
        public List<ConformanceRestQueryComponent> getQuery() { 
          return this.query;
        }

    // syntactic sugar
        /**
         * @return {@link #query} (Definition of a named query and its parameters and their meaning.)
         */
        public ConformanceRestQueryComponent addQuery() { 
          ConformanceRestQueryComponent t = new ConformanceRestQueryComponent();
          this.query.add(t);
          return t;
        }

        /**
         * @return {@link #documentMailbox} (A list of profiles that this server implements for accepting documents on mailbox. If the list is empty, then documents are not accepted. The base specification has the profile identifier "http://hl7.org/fhir/documents/mailbox". Other specifications can declare their own identifier for this use.)
         */
        public List<Uri> getDocumentMailbox() { 
          return this.documentMailbox;
        }

    // syntactic sugar
        /**
         * @return {@link #documentMailbox} (A list of profiles that this server implements for accepting documents on mailbox. If the list is empty, then documents are not accepted. The base specification has the profile identifier "http://hl7.org/fhir/documents/mailbox". Other specifications can declare their own identifier for this use.)
         */
        public Uri addDocumentMailbox() { 
          Uri t = new Uri();
          this.documentMailbox.add(t);
          return t;
        }

        /**
         * @param value {@link #documentMailbox} (A list of profiles that this server implements for accepting documents on mailbox. If the list is empty, then documents are not accepted. The base specification has the profile identifier "http://hl7.org/fhir/documents/mailbox". Other specifications can declare their own identifier for this use.)
         */
        public Uri addDocumentMailboxSimple(String value) { 
          Uri t = new Uri();
          t.setValue(value);
          this.documentMailbox.add(t);
          return t;
        }

        protected void listChildren(List<Property> childrenList) {
          super.listChildren(childrenList);
          childrenList.add(new Property("mode", "code", "Identifies whether this portion of the statement is describing ability to initiate or receive restful operations.", 0, java.lang.Integer.MAX_VALUE, mode));
          childrenList.add(new Property("documentation", "string", "Provides documentation about the system's restful capabilities that apply across all applications, such as security.", 0, java.lang.Integer.MAX_VALUE, documentation));
          childrenList.add(new Property("security", "", "Information about security of implementation.", 0, java.lang.Integer.MAX_VALUE, security));
          childrenList.add(new Property("resource", "", "Identifies the restful capabilities of the solution for a specific resource type.", 0, java.lang.Integer.MAX_VALUE, resource));
          childrenList.add(new Property("operation", "", "Identifies a restful operation supported by the solution.", 0, java.lang.Integer.MAX_VALUE, operation));
          childrenList.add(new Property("query", "", "Definition of a named query and its parameters and their meaning.", 0, java.lang.Integer.MAX_VALUE, query));
          childrenList.add(new Property("documentMailbox", "uri", "A list of profiles that this server implements for accepting documents on mailbox. If the list is empty, then documents are not accepted. The base specification has the profile identifier 'http://hl7.org/fhir/documents/mailbox'. Other specifications can declare their own identifier for this use.", 0, java.lang.Integer.MAX_VALUE, documentMailbox));
        }

      public ConformanceRestComponent copy(Conformance e) {
        ConformanceRestComponent dst = new ConformanceRestComponent();
        dst.mode = mode == null ? null : mode.copy();
        dst.documentation = documentation == null ? null : documentation.copy();
        dst.security = security == null ? null : security.copy(e);
        dst.resource = new ArrayList<ConformanceRestResourceComponent>();
        for (ConformanceRestResourceComponent i : resource)
          dst.resource.add(i.copy(e));
        dst.operation = new ArrayList<ConformanceRestOperationComponent>();
        for (ConformanceRestOperationComponent i : operation)
          dst.operation.add(i.copy(e));
        dst.query = new ArrayList<ConformanceRestQueryComponent>();
        for (ConformanceRestQueryComponent i : query)
          dst.query.add(i.copy(e));
        dst.documentMailbox = new ArrayList<Uri>();
        for (Uri i : documentMailbox)
          dst.documentMailbox.add(i.copy());
        return dst;
      }

  }

    public static class ConformanceRestSecurityComponent extends BackboneElement {
        /**
         * Server adds CORS headers when responding to reuqests - this enables javascript applications to yuse the server.
         */
        protected Boolean cors;

        /**
         * What type of security services are supported/required.
         */
        protected List<CodeableConcept> service = new ArrayList<CodeableConcept>();

        /**
         * General description of how security works.
         */
        protected String_ description;

        /**
         * Certificates associated with security profiles.
         */
        protected List<ConformanceRestSecurityCertificateComponent> certificate = new ArrayList<ConformanceRestSecurityCertificateComponent>();

      public ConformanceRestSecurityComponent() {
        super();
      }

        /**
         * @return {@link #cors} (Server adds CORS headers when responding to reuqests - this enables javascript applications to yuse the server.)
         */
        public Boolean getCors() { 
          return this.cors;
        }

        /**
         * @param value {@link #cors} (Server adds CORS headers when responding to reuqests - this enables javascript applications to yuse the server.)
         */
        public ConformanceRestSecurityComponent setCors(Boolean value) { 
          this.cors = value;
          return this;
        }

        /**
         * @return Server adds CORS headers when responding to reuqests - this enables javascript applications to yuse the server.
         */
        public boolean getCorsSimple() { 
          return this.cors == null ? null : this.cors.getValue();
        }

        /**
         * @param value Server adds CORS headers when responding to reuqests - this enables javascript applications to yuse the server.
         */
        public ConformanceRestSecurityComponent setCorsSimple(boolean value) { 
          if (value == false)
            this.cors = null;
          else {
            if (this.cors == null)
              this.cors = new Boolean();
            this.cors.setValue(value);
          }
          return this;
        }

        /**
         * @return {@link #service} (What type of security services are supported/required.)
         */
        public List<CodeableConcept> getService() { 
          return this.service;
        }

    // syntactic sugar
        /**
         * @return {@link #service} (What type of security services are supported/required.)
         */
        public CodeableConcept addService() { 
          CodeableConcept t = new CodeableConcept();
          this.service.add(t);
          return t;
        }

        /**
         * @return {@link #description} (General description of how security works.)
         */
        public String_ getDescription() { 
          return this.description;
        }

        /**
         * @param value {@link #description} (General description of how security works.)
         */
        public ConformanceRestSecurityComponent setDescription(String_ value) { 
          this.description = value;
          return this;
        }

        /**
         * @return General description of how security works.
         */
        public String getDescriptionSimple() { 
          return this.description == null ? null : this.description.getValue();
        }

        /**
         * @param value General description of how security works.
         */
        public ConformanceRestSecurityComponent setDescriptionSimple(String value) { 
          if (value == null)
            this.description = null;
          else {
            if (this.description == null)
              this.description = new String_();
            this.description.setValue(value);
          }
          return this;
        }

        /**
         * @return {@link #certificate} (Certificates associated with security profiles.)
         */
        public List<ConformanceRestSecurityCertificateComponent> getCertificate() { 
          return this.certificate;
        }

    // syntactic sugar
        /**
         * @return {@link #certificate} (Certificates associated with security profiles.)
         */
        public ConformanceRestSecurityCertificateComponent addCertificate() { 
          ConformanceRestSecurityCertificateComponent t = new ConformanceRestSecurityCertificateComponent();
          this.certificate.add(t);
          return t;
        }

        protected void listChildren(List<Property> childrenList) {
          super.listChildren(childrenList);
          childrenList.add(new Property("cors", "boolean", "Server adds CORS headers when responding to reuqests - this enables javascript applications to yuse the server.", 0, java.lang.Integer.MAX_VALUE, cors));
          childrenList.add(new Property("service", "CodeableConcept", "What type of security services are supported/required.", 0, java.lang.Integer.MAX_VALUE, service));
          childrenList.add(new Property("description", "string", "General description of how security works.", 0, java.lang.Integer.MAX_VALUE, description));
          childrenList.add(new Property("certificate", "", "Certificates associated with security profiles.", 0, java.lang.Integer.MAX_VALUE, certificate));
        }

      public ConformanceRestSecurityComponent copy(Conformance e) {
        ConformanceRestSecurityComponent dst = new ConformanceRestSecurityComponent();
        dst.cors = cors == null ? null : cors.copy();
        dst.service = new ArrayList<CodeableConcept>();
        for (CodeableConcept i : service)
          dst.service.add(i.copy());
        dst.description = description == null ? null : description.copy();
        dst.certificate = new ArrayList<ConformanceRestSecurityCertificateComponent>();
        for (ConformanceRestSecurityCertificateComponent i : certificate)
          dst.certificate.add(i.copy(e));
        return dst;
      }

  }

    public static class ConformanceRestSecurityCertificateComponent extends BackboneElement {
        /**
         * Mime type for certificate.
         */
        protected Code type;

        /**
         * Actual certificate.
         */
        protected Base64Binary blob;

      public ConformanceRestSecurityCertificateComponent() {
        super();
      }

        /**
         * @return {@link #type} (Mime type for certificate.)
         */
        public Code getType() { 
          return this.type;
        }

        /**
         * @param value {@link #type} (Mime type for certificate.)
         */
        public ConformanceRestSecurityCertificateComponent setType(Code value) { 
          this.type = value;
          return this;
        }

        /**
         * @return Mime type for certificate.
         */
        public String getTypeSimple() { 
          return this.type == null ? null : this.type.getValue();
        }

        /**
         * @param value Mime type for certificate.
         */
        public ConformanceRestSecurityCertificateComponent setTypeSimple(String value) { 
          if (value == null)
            this.type = null;
          else {
            if (this.type == null)
              this.type = new Code();
            this.type.setValue(value);
          }
          return this;
        }

        /**
         * @return {@link #blob} (Actual certificate.)
         */
        public Base64Binary getBlob() { 
          return this.blob;
        }

        /**
         * @param value {@link #blob} (Actual certificate.)
         */
        public ConformanceRestSecurityCertificateComponent setBlob(Base64Binary value) { 
          this.blob = value;
          return this;
        }

        /**
         * @return Actual certificate.
         */
        public byte[] getBlobSimple() { 
          return this.blob == null ? null : this.blob.getValue();
        }

        /**
         * @param value Actual certificate.
         */
        public ConformanceRestSecurityCertificateComponent setBlobSimple(byte[] value) { 
          if (value == null)
            this.blob = null;
          else {
            if (this.blob == null)
              this.blob = new Base64Binary();
            this.blob.setValue(value);
          }
          return this;
        }

        protected void listChildren(List<Property> childrenList) {
          super.listChildren(childrenList);
          childrenList.add(new Property("type", "code", "Mime type for certificate.", 0, java.lang.Integer.MAX_VALUE, type));
          childrenList.add(new Property("blob", "base64Binary", "Actual certificate.", 0, java.lang.Integer.MAX_VALUE, blob));
        }

      public ConformanceRestSecurityCertificateComponent copy(Conformance e) {
        ConformanceRestSecurityCertificateComponent dst = new ConformanceRestSecurityCertificateComponent();
        dst.type = type == null ? null : type.copy();
        dst.blob = blob == null ? null : blob.copy();
        return dst;
      }

  }

    public static class ConformanceRestResourceComponent extends BackboneElement {
        /**
         * Identifies the resource exposed via the restful interface.
         */
        protected Code type;

        /**
         * Identifies the profile that describes the solution's support for the resource, including any constraints on cardinality, bindings, lengths or other limitations.
         */
        protected ResourceReference profile;

        /**
         * Identifies a restful operation supported by the solution.
         */
        protected List<ConformanceRestResourceOperationComponent> operation = new ArrayList<ConformanceRestResourceOperationComponent>();

        /**
         * A flag for whether the server is able to return past versions as part of the vRead operation.
         */
        protected Boolean readHistory;

        /**
         * If the update operation is used (client) or allowed (server) to a new location where a resource doesn't already exist. This means that the server allows the client to create new identities on the server.
         */
        protected Boolean updateCreate;

        /**
         * _include values supported by the server.
         */
        protected List<String_> searchInclude = new ArrayList<String_>();

        /**
         * Defines additional search parameters for implementations to support and/or make use of.
         */
        protected List<ConformanceRestResourceSearchParamComponent> searchParam = new ArrayList<ConformanceRestResourceSearchParamComponent>();

      public ConformanceRestResourceComponent() {
        super();
      }

      public ConformanceRestResourceComponent(Code type) {
        super();
        this.type = type;
      }

        /**
         * @return {@link #type} (Identifies the resource exposed via the restful interface.)
         */
        public Code getType() { 
          return this.type;
        }

        /**
         * @param value {@link #type} (Identifies the resource exposed via the restful interface.)
         */
        public ConformanceRestResourceComponent setType(Code value) { 
          this.type = value;
          return this;
        }

        /**
         * @return Identifies the resource exposed via the restful interface.
         */
        public String getTypeSimple() { 
          return this.type == null ? null : this.type.getValue();
        }

        /**
         * @param value Identifies the resource exposed via the restful interface.
         */
        public ConformanceRestResourceComponent setTypeSimple(String value) { 
            if (this.type == null)
              this.type = new Code();
            this.type.setValue(value);
          return this;
        }

        /**
         * @return {@link #profile} (Identifies the profile that describes the solution's support for the resource, including any constraints on cardinality, bindings, lengths or other limitations.)
         */
        public ResourceReference getProfile() { 
          return this.profile;
        }

        /**
         * @param value {@link #profile} (Identifies the profile that describes the solution's support for the resource, including any constraints on cardinality, bindings, lengths or other limitations.)
         */
        public ConformanceRestResourceComponent setProfile(ResourceReference value) { 
          this.profile = value;
          return this;
        }

        /**
         * @return {@link #operation} (Identifies a restful operation supported by the solution.)
         */
        public List<ConformanceRestResourceOperationComponent> getOperation() { 
          return this.operation;
        }

    // syntactic sugar
        /**
         * @return {@link #operation} (Identifies a restful operation supported by the solution.)
         */
        public ConformanceRestResourceOperationComponent addOperation() { 
          ConformanceRestResourceOperationComponent t = new ConformanceRestResourceOperationComponent();
          this.operation.add(t);
          return t;
        }

        /**
         * @return {@link #readHistory} (A flag for whether the server is able to return past versions as part of the vRead operation.)
         */
        public Boolean getReadHistory() { 
          return this.readHistory;
        }

        /**
         * @param value {@link #readHistory} (A flag for whether the server is able to return past versions as part of the vRead operation.)
         */
        public ConformanceRestResourceComponent setReadHistory(Boolean value) { 
          this.readHistory = value;
          return this;
        }

        /**
         * @return A flag for whether the server is able to return past versions as part of the vRead operation.
         */
        public boolean getReadHistorySimple() { 
          return this.readHistory == null ? null : this.readHistory.getValue();
        }

        /**
         * @param value A flag for whether the server is able to return past versions as part of the vRead operation.
         */
        public ConformanceRestResourceComponent setReadHistorySimple(boolean value) { 
          if (value == false)
            this.readHistory = null;
          else {
            if (this.readHistory == null)
              this.readHistory = new Boolean();
            this.readHistory.setValue(value);
          }
          return this;
        }

        /**
         * @return {@link #updateCreate} (If the update operation is used (client) or allowed (server) to a new location where a resource doesn't already exist. This means that the server allows the client to create new identities on the server.)
         */
        public Boolean getUpdateCreate() { 
          return this.updateCreate;
        }

        /**
         * @param value {@link #updateCreate} (If the update operation is used (client) or allowed (server) to a new location where a resource doesn't already exist. This means that the server allows the client to create new identities on the server.)
         */
        public ConformanceRestResourceComponent setUpdateCreate(Boolean value) { 
          this.updateCreate = value;
          return this;
        }

        /**
         * @return If the update operation is used (client) or allowed (server) to a new location where a resource doesn't already exist. This means that the server allows the client to create new identities on the server.
         */
        public boolean getUpdateCreateSimple() { 
          return this.updateCreate == null ? null : this.updateCreate.getValue();
        }

        /**
         * @param value If the update operation is used (client) or allowed (server) to a new location where a resource doesn't already exist. This means that the server allows the client to create new identities on the server.
         */
        public ConformanceRestResourceComponent setUpdateCreateSimple(boolean value) { 
          if (value == false)
            this.updateCreate = null;
          else {
            if (this.updateCreate == null)
              this.updateCreate = new Boolean();
            this.updateCreate.setValue(value);
          }
          return this;
        }

        /**
         * @return {@link #searchInclude} (_include values supported by the server.)
         */
        public List<String_> getSearchInclude() { 
          return this.searchInclude;
        }

    // syntactic sugar
        /**
         * @return {@link #searchInclude} (_include values supported by the server.)
         */
        public String_ addSearchInclude() { 
          String_ t = new String_();
          this.searchInclude.add(t);
          return t;
        }

        /**
         * @param value {@link #searchInclude} (_include values supported by the server.)
         */
        public String_ addSearchIncludeSimple(String value) { 
          String_ t = new String_();
          t.setValue(value);
          this.searchInclude.add(t);
          return t;
        }

        /**
         * @return {@link #searchParam} (Defines additional search parameters for implementations to support and/or make use of.)
         */
        public List<ConformanceRestResourceSearchParamComponent> getSearchParam() { 
          return this.searchParam;
        }

    // syntactic sugar
        /**
         * @return {@link #searchParam} (Defines additional search parameters for implementations to support and/or make use of.)
         */
        public ConformanceRestResourceSearchParamComponent addSearchParam() { 
          ConformanceRestResourceSearchParamComponent t = new ConformanceRestResourceSearchParamComponent();
          this.searchParam.add(t);
          return t;
        }

        protected void listChildren(List<Property> childrenList) {
          super.listChildren(childrenList);
          childrenList.add(new Property("type", "code", "Identifies the resource exposed via the restful interface.", 0, java.lang.Integer.MAX_VALUE, type));
          childrenList.add(new Property("profile", "Resource(Profile)", "Identifies the profile that describes the solution's support for the resource, including any constraints on cardinality, bindings, lengths or other limitations.", 0, java.lang.Integer.MAX_VALUE, profile));
          childrenList.add(new Property("operation", "", "Identifies a restful operation supported by the solution.", 0, java.lang.Integer.MAX_VALUE, operation));
          childrenList.add(new Property("readHistory", "boolean", "A flag for whether the server is able to return past versions as part of the vRead operation.", 0, java.lang.Integer.MAX_VALUE, readHistory));
          childrenList.add(new Property("updateCreate", "boolean", "If the update operation is used (client) or allowed (server) to a new location where a resource doesn't already exist. This means that the server allows the client to create new identities on the server.", 0, java.lang.Integer.MAX_VALUE, updateCreate));
          childrenList.add(new Property("searchInclude", "string", "_include values supported by the server.", 0, java.lang.Integer.MAX_VALUE, searchInclude));
          childrenList.add(new Property("searchParam", "", "Defines additional search parameters for implementations to support and/or make use of.", 0, java.lang.Integer.MAX_VALUE, searchParam));
        }

      public ConformanceRestResourceComponent copy(Conformance e) {
        ConformanceRestResourceComponent dst = new ConformanceRestResourceComponent();
        dst.type = type == null ? null : type.copy();
        dst.profile = profile == null ? null : profile.copy();
        dst.operation = new ArrayList<ConformanceRestResourceOperationComponent>();
        for (ConformanceRestResourceOperationComponent i : operation)
          dst.operation.add(i.copy(e));
        dst.readHistory = readHistory == null ? null : readHistory.copy();
        dst.updateCreate = updateCreate == null ? null : updateCreate.copy();
        dst.searchInclude = new ArrayList<String_>();
        for (String_ i : searchInclude)
          dst.searchInclude.add(i.copy());
        dst.searchParam = new ArrayList<ConformanceRestResourceSearchParamComponent>();
        for (ConformanceRestResourceSearchParamComponent i : searchParam)
          dst.searchParam.add(i.copy(e));
        return dst;
      }

  }

    public static class ConformanceRestResourceOperationComponent extends BackboneElement {
        /**
         * Identifies which operation is supported.
         */
        protected Enumeration<TypeRestfulOperation> code;

        /**
         * Provides guidance specific to the implementation of this operation, such as 'delete is a logical delete' or 'updates are only allowed with version id' or 'creates permitted from pre-authorized certificates only'.
         */
        protected String_ documentation;

      public ConformanceRestResourceOperationComponent() {
        super();
      }

      public ConformanceRestResourceOperationComponent(Enumeration<TypeRestfulOperation> code) {
        super();
        this.code = code;
      }

        /**
         * @return {@link #code} (Identifies which operation is supported.)
         */
        public Enumeration<TypeRestfulOperation> getCode() { 
          return this.code;
        }

        /**
         * @param value {@link #code} (Identifies which operation is supported.)
         */
        public ConformanceRestResourceOperationComponent setCode(Enumeration<TypeRestfulOperation> value) { 
          this.code = value;
          return this;
        }

        /**
         * @return Identifies which operation is supported.
         */
        public TypeRestfulOperation getCodeSimple() { 
          return this.code == null ? null : this.code.getValue();
        }

        /**
         * @param value Identifies which operation is supported.
         */
        public ConformanceRestResourceOperationComponent setCodeSimple(TypeRestfulOperation value) { 
            if (this.code == null)
              this.code = new Enumeration<TypeRestfulOperation>();
            this.code.setValue(value);
          return this;
        }

        /**
         * @return {@link #documentation} (Provides guidance specific to the implementation of this operation, such as 'delete is a logical delete' or 'updates are only allowed with version id' or 'creates permitted from pre-authorized certificates only'.)
         */
        public String_ getDocumentation() { 
          return this.documentation;
        }

        /**
         * @param value {@link #documentation} (Provides guidance specific to the implementation of this operation, such as 'delete is a logical delete' or 'updates are only allowed with version id' or 'creates permitted from pre-authorized certificates only'.)
         */
        public ConformanceRestResourceOperationComponent setDocumentation(String_ value) { 
          this.documentation = value;
          return this;
        }

        /**
         * @return Provides guidance specific to the implementation of this operation, such as 'delete is a logical delete' or 'updates are only allowed with version id' or 'creates permitted from pre-authorized certificates only'.
         */
        public String getDocumentationSimple() { 
          return this.documentation == null ? null : this.documentation.getValue();
        }

        /**
         * @param value Provides guidance specific to the implementation of this operation, such as 'delete is a logical delete' or 'updates are only allowed with version id' or 'creates permitted from pre-authorized certificates only'.
         */
        public ConformanceRestResourceOperationComponent setDocumentationSimple(String value) { 
          if (value == null)
            this.documentation = null;
          else {
            if (this.documentation == null)
              this.documentation = new String_();
            this.documentation.setValue(value);
          }
          return this;
        }

        protected void listChildren(List<Property> childrenList) {
          super.listChildren(childrenList);
          childrenList.add(new Property("code", "code", "Identifies which operation is supported.", 0, java.lang.Integer.MAX_VALUE, code));
          childrenList.add(new Property("documentation", "string", "Provides guidance specific to the implementation of this operation, such as 'delete is a logical delete' or 'updates are only allowed with version id' or 'creates permitted from pre-authorized certificates only'.", 0, java.lang.Integer.MAX_VALUE, documentation));
        }

      public ConformanceRestResourceOperationComponent copy(Conformance e) {
        ConformanceRestResourceOperationComponent dst = new ConformanceRestResourceOperationComponent();
        dst.code = code == null ? null : code.copy();
        dst.documentation = documentation == null ? null : documentation.copy();
        return dst;
      }

  }

    public static class ConformanceRestResourceSearchParamComponent extends BackboneElement {
        /**
         * Corresponds to the name of the standard or custom search parameter.
         */
        protected String_ name;

        /**
         * A formal reference to where this parameter was first defined, so that a client can be confident of the meaning of the search parameter.
         */
        protected Uri source;

        /**
         * The type of value a search parameter refers to, and how the content is interpreted.
         */
        protected Enumeration<SearchParamType> type;

        /**
         * For standard parameters, provides additional information on how the parameter is used in this solution.  For custom parameters, provides a description of what the parameter does.
         */
        protected String_ documentation;

        /**
         * An XPath expression that extracts the set of elements that contain values that a search parameter matches.
         */
        protected String_ xpath;

        /**
         * Types of resource (if a resource reference).
         */
        protected List<Code> target = new ArrayList<Code>();

        /**
         * Chained names supported.
         */
        protected List<String_> chain = new ArrayList<String_>();

      public ConformanceRestResourceSearchParamComponent() {
        super();
      }

      public ConformanceRestResourceSearchParamComponent(String_ name, Enumeration<SearchParamType> type, String_ documentation) {
        super();
        this.name = name;
        this.type = type;
        this.documentation = documentation;
      }

        /**
         * @return {@link #name} (Corresponds to the name of the standard or custom search parameter.)
         */
        public String_ getName() { 
          return this.name;
        }

        /**
         * @param value {@link #name} (Corresponds to the name of the standard or custom search parameter.)
         */
        public ConformanceRestResourceSearchParamComponent setName(String_ value) { 
          this.name = value;
          return this;
        }

        /**
         * @return Corresponds to the name of the standard or custom search parameter.
         */
        public String getNameSimple() { 
          return this.name == null ? null : this.name.getValue();
        }

        /**
         * @param value Corresponds to the name of the standard or custom search parameter.
         */
        public ConformanceRestResourceSearchParamComponent setNameSimple(String value) { 
            if (this.name == null)
              this.name = new String_();
            this.name.setValue(value);
          return this;
        }

        /**
         * @return {@link #source} (A formal reference to where this parameter was first defined, so that a client can be confident of the meaning of the search parameter.)
         */
        public Uri getSource() { 
          return this.source;
        }

        /**
         * @param value {@link #source} (A formal reference to where this parameter was first defined, so that a client can be confident of the meaning of the search parameter.)
         */
        public ConformanceRestResourceSearchParamComponent setSource(Uri value) { 
          this.source = value;
          return this;
        }

        /**
         * @return A formal reference to where this parameter was first defined, so that a client can be confident of the meaning of the search parameter.
         */
        public String getSourceSimple() { 
          return this.source == null ? null : this.source.getValue();
        }

        /**
         * @param value A formal reference to where this parameter was first defined, so that a client can be confident of the meaning of the search parameter.
         */
        public ConformanceRestResourceSearchParamComponent setSourceSimple(String value) { 
          if (value == null)
            this.source = null;
          else {
            if (this.source == null)
              this.source = new Uri();
            this.source.setValue(value);
          }
          return this;
        }

        /**
         * @return {@link #type} (The type of value a search parameter refers to, and how the content is interpreted.)
         */
        public Enumeration<SearchParamType> getType() { 
          return this.type;
        }

        /**
         * @param value {@link #type} (The type of value a search parameter refers to, and how the content is interpreted.)
         */
        public ConformanceRestResourceSearchParamComponent setType(Enumeration<SearchParamType> value) { 
          this.type = value;
          return this;
        }

        /**
         * @return The type of value a search parameter refers to, and how the content is interpreted.
         */
        public SearchParamType getTypeSimple() { 
          return this.type == null ? null : this.type.getValue();
        }

        /**
         * @param value The type of value a search parameter refers to, and how the content is interpreted.
         */
        public ConformanceRestResourceSearchParamComponent setTypeSimple(SearchParamType value) { 
            if (this.type == null)
              this.type = new Enumeration<SearchParamType>();
            this.type.setValue(value);
          return this;
        }

        /**
         * @return {@link #documentation} (For standard parameters, provides additional information on how the parameter is used in this solution.  For custom parameters, provides a description of what the parameter does.)
         */
        public String_ getDocumentation() { 
          return this.documentation;
        }

        /**
         * @param value {@link #documentation} (For standard parameters, provides additional information on how the parameter is used in this solution.  For custom parameters, provides a description of what the parameter does.)
         */
        public ConformanceRestResourceSearchParamComponent setDocumentation(String_ value) { 
          this.documentation = value;
          return this;
        }

        /**
         * @return For standard parameters, provides additional information on how the parameter is used in this solution.  For custom parameters, provides a description of what the parameter does.
         */
        public String getDocumentationSimple() { 
          return this.documentation == null ? null : this.documentation.getValue();
        }

        /**
         * @param value For standard parameters, provides additional information on how the parameter is used in this solution.  For custom parameters, provides a description of what the parameter does.
         */
        public ConformanceRestResourceSearchParamComponent setDocumentationSimple(String value) { 
            if (this.documentation == null)
              this.documentation = new String_();
            this.documentation.setValue(value);
          return this;
        }

        /**
         * @return {@link #xpath} (An XPath expression that extracts the set of elements that contain values that a search parameter matches.)
         */
        public String_ getXpath() { 
          return this.xpath;
        }

        /**
         * @param value {@link #xpath} (An XPath expression that extracts the set of elements that contain values that a search parameter matches.)
         */
        public ConformanceRestResourceSearchParamComponent setXpath(String_ value) { 
          this.xpath = value;
          return this;
        }

        /**
         * @return An XPath expression that extracts the set of elements that contain values that a search parameter matches.
         */
        public String getXpathSimple() { 
          return this.xpath == null ? null : this.xpath.getValue();
        }

        /**
         * @param value An XPath expression that extracts the set of elements that contain values that a search parameter matches.
         */
        public ConformanceRestResourceSearchParamComponent setXpathSimple(String value) { 
          if (value == null)
            this.xpath = null;
          else {
            if (this.xpath == null)
              this.xpath = new String_();
            this.xpath.setValue(value);
          }
          return this;
        }

        /**
         * @return {@link #target} (Types of resource (if a resource reference).)
         */
        public List<Code> getTarget() { 
          return this.target;
        }

    // syntactic sugar
        /**
         * @return {@link #target} (Types of resource (if a resource reference).)
         */
        public Code addTarget() { 
          Code t = new Code();
          this.target.add(t);
          return t;
        }

        /**
         * @param value {@link #target} (Types of resource (if a resource reference).)
         */
        public Code addTargetSimple(String value) { 
          Code t = new Code();
          t.setValue(value);
          this.target.add(t);
          return t;
        }

        /**
         * @return {@link #chain} (Chained names supported.)
         */
        public List<String_> getChain() { 
          return this.chain;
        }

    // syntactic sugar
        /**
         * @return {@link #chain} (Chained names supported.)
         */
        public String_ addChain() { 
          String_ t = new String_();
          this.chain.add(t);
          return t;
        }

        /**
         * @param value {@link #chain} (Chained names supported.)
         */
        public String_ addChainSimple(String value) { 
          String_ t = new String_();
          t.setValue(value);
          this.chain.add(t);
          return t;
        }

        protected void listChildren(List<Property> childrenList) {
          super.listChildren(childrenList);
          childrenList.add(new Property("name", "string", "Corresponds to the name of the standard or custom search parameter.", 0, java.lang.Integer.MAX_VALUE, name));
          childrenList.add(new Property("source", "uri", "A formal reference to where this parameter was first defined, so that a client can be confident of the meaning of the search parameter.", 0, java.lang.Integer.MAX_VALUE, source));
          childrenList.add(new Property("type", "code", "The type of value a search parameter refers to, and how the content is interpreted.", 0, java.lang.Integer.MAX_VALUE, type));
          childrenList.add(new Property("documentation", "string", "For standard parameters, provides additional information on how the parameter is used in this solution.  For custom parameters, provides a description of what the parameter does.", 0, java.lang.Integer.MAX_VALUE, documentation));
          childrenList.add(new Property("xpath", "string", "An XPath expression that extracts the set of elements that contain values that a search parameter matches.", 0, java.lang.Integer.MAX_VALUE, xpath));
          childrenList.add(new Property("target", "code", "Types of resource (if a resource reference).", 0, java.lang.Integer.MAX_VALUE, target));
          childrenList.add(new Property("chain", "string", "Chained names supported.", 0, java.lang.Integer.MAX_VALUE, chain));
        }

      public ConformanceRestResourceSearchParamComponent copy(Conformance e) {
        ConformanceRestResourceSearchParamComponent dst = new ConformanceRestResourceSearchParamComponent();
        dst.name = name == null ? null : name.copy();
        dst.source = source == null ? null : source.copy();
        dst.type = type == null ? null : type.copy();
        dst.documentation = documentation == null ? null : documentation.copy();
        dst.xpath = xpath == null ? null : xpath.copy();
        dst.target = new ArrayList<Code>();
        for (Code i : target)
          dst.target.add(i.copy());
        dst.chain = new ArrayList<String_>();
        for (String_ i : chain)
          dst.chain.add(i.copy());
        return dst;
      }

  }

    public static class ConformanceRestOperationComponent extends BackboneElement {
        /**
         * Identifies which system operation is supported.
         */
        protected Enumeration<SystemRestfulOperation> code;

        /**
         * Provides guidance specific to the implementation of this operation, such as limitations on the kind of transactions allowed, or information about system wide search is implemented.
         */
        protected String_ documentation;

      public ConformanceRestOperationComponent() {
        super();
      }

      public ConformanceRestOperationComponent(Enumeration<SystemRestfulOperation> code) {
        super();
        this.code = code;
      }

        /**
         * @return {@link #code} (Identifies which system operation is supported.)
         */
        public Enumeration<SystemRestfulOperation> getCode() { 
          return this.code;
        }

        /**
         * @param value {@link #code} (Identifies which system operation is supported.)
         */
        public ConformanceRestOperationComponent setCode(Enumeration<SystemRestfulOperation> value) { 
          this.code = value;
          return this;
        }

        /**
         * @return Identifies which system operation is supported.
         */
        public SystemRestfulOperation getCodeSimple() { 
          return this.code == null ? null : this.code.getValue();
        }

        /**
         * @param value Identifies which system operation is supported.
         */
        public ConformanceRestOperationComponent setCodeSimple(SystemRestfulOperation value) { 
            if (this.code == null)
              this.code = new Enumeration<SystemRestfulOperation>();
            this.code.setValue(value);
          return this;
        }

        /**
         * @return {@link #documentation} (Provides guidance specific to the implementation of this operation, such as limitations on the kind of transactions allowed, or information about system wide search is implemented.)
         */
        public String_ getDocumentation() { 
          return this.documentation;
        }

        /**
         * @param value {@link #documentation} (Provides guidance specific to the implementation of this operation, such as limitations on the kind of transactions allowed, or information about system wide search is implemented.)
         */
        public ConformanceRestOperationComponent setDocumentation(String_ value) { 
          this.documentation = value;
          return this;
        }

        /**
         * @return Provides guidance specific to the implementation of this operation, such as limitations on the kind of transactions allowed, or information about system wide search is implemented.
         */
        public String getDocumentationSimple() { 
          return this.documentation == null ? null : this.documentation.getValue();
        }

        /**
         * @param value Provides guidance specific to the implementation of this operation, such as limitations on the kind of transactions allowed, or information about system wide search is implemented.
         */
        public ConformanceRestOperationComponent setDocumentationSimple(String value) { 
          if (value == null)
            this.documentation = null;
          else {
            if (this.documentation == null)
              this.documentation = new String_();
            this.documentation.setValue(value);
          }
          return this;
        }

        protected void listChildren(List<Property> childrenList) {
          super.listChildren(childrenList);
          childrenList.add(new Property("code", "code", "Identifies which system operation is supported.", 0, java.lang.Integer.MAX_VALUE, code));
          childrenList.add(new Property("documentation", "string", "Provides guidance specific to the implementation of this operation, such as limitations on the kind of transactions allowed, or information about system wide search is implemented.", 0, java.lang.Integer.MAX_VALUE, documentation));
        }

      public ConformanceRestOperationComponent copy(Conformance e) {
        ConformanceRestOperationComponent dst = new ConformanceRestOperationComponent();
        dst.code = code == null ? null : code.copy();
        dst.documentation = documentation == null ? null : documentation.copy();
        return dst;
      }

  }

    public static class ConformanceRestQueryComponent extends BackboneElement {
        /**
         * The name of this query, which is used in the _query parameter when the query is used.
         */
        protected String_ name;

        /**
         * Description of the query - the functionality it offers, and considerations about how it functions and to use it.
         */
        protected String_ documentation;

        /**
         * Parameter for the named query.
         */
        protected List<ConformanceRestResourceSearchParamComponent> parameter = new ArrayList<ConformanceRestResourceSearchParamComponent>();

      public ConformanceRestQueryComponent() {
        super();
      }

      public ConformanceRestQueryComponent(String_ name, String_ documentation) {
        super();
        this.name = name;
        this.documentation = documentation;
      }

        /**
         * @return {@link #name} (The name of this query, which is used in the _query parameter when the query is used.)
         */
        public String_ getName() { 
          return this.name;
        }

        /**
         * @param value {@link #name} (The name of this query, which is used in the _query parameter when the query is used.)
         */
        public ConformanceRestQueryComponent setName(String_ value) { 
          this.name = value;
          return this;
        }

        /**
         * @return The name of this query, which is used in the _query parameter when the query is used.
         */
        public String getNameSimple() { 
          return this.name == null ? null : this.name.getValue();
        }

        /**
         * @param value The name of this query, which is used in the _query parameter when the query is used.
         */
        public ConformanceRestQueryComponent setNameSimple(String value) { 
            if (this.name == null)
              this.name = new String_();
            this.name.setValue(value);
          return this;
        }

        /**
         * @return {@link #documentation} (Description of the query - the functionality it offers, and considerations about how it functions and to use it.)
         */
        public String_ getDocumentation() { 
          return this.documentation;
        }

        /**
         * @param value {@link #documentation} (Description of the query - the functionality it offers, and considerations about how it functions and to use it.)
         */
        public ConformanceRestQueryComponent setDocumentation(String_ value) { 
          this.documentation = value;
          return this;
        }

        /**
         * @return Description of the query - the functionality it offers, and considerations about how it functions and to use it.
         */
        public String getDocumentationSimple() { 
          return this.documentation == null ? null : this.documentation.getValue();
        }

        /**
         * @param value Description of the query - the functionality it offers, and considerations about how it functions and to use it.
         */
        public ConformanceRestQueryComponent setDocumentationSimple(String value) { 
            if (this.documentation == null)
              this.documentation = new String_();
            this.documentation.setValue(value);
          return this;
        }

        /**
         * @return {@link #parameter} (Parameter for the named query.)
         */
        public List<ConformanceRestResourceSearchParamComponent> getParameter() { 
          return this.parameter;
        }

    // syntactic sugar
        /**
         * @return {@link #parameter} (Parameter for the named query.)
         */
        public ConformanceRestResourceSearchParamComponent addParameter() { 
          ConformanceRestResourceSearchParamComponent t = new ConformanceRestResourceSearchParamComponent();
          this.parameter.add(t);
          return t;
        }

        protected void listChildren(List<Property> childrenList) {
          super.listChildren(childrenList);
          childrenList.add(new Property("name", "string", "The name of this query, which is used in the _query parameter when the query is used.", 0, java.lang.Integer.MAX_VALUE, name));
          childrenList.add(new Property("documentation", "string", "Description of the query - the functionality it offers, and considerations about how it functions and to use it.", 0, java.lang.Integer.MAX_VALUE, documentation));
          childrenList.add(new Property("parameter", "@Conformance.rest.resource.searchParam", "Parameter for the named query.", 0, java.lang.Integer.MAX_VALUE, parameter));
        }

      public ConformanceRestQueryComponent copy(Conformance e) {
        ConformanceRestQueryComponent dst = new ConformanceRestQueryComponent();
        dst.name = name == null ? null : name.copy();
        dst.documentation = documentation == null ? null : documentation.copy();
        dst.parameter = new ArrayList<ConformanceRestResourceSearchParamComponent>();
        for (ConformanceRestResourceSearchParamComponent i : parameter)
          dst.parameter.add(i.copy(e));
        return dst;
      }

  }

    public static class ConformanceMessagingComponent extends BackboneElement {
        /**
         * The address to which messages and/or replies are to be sent.
         */
        protected Uri endpoint;

        /**
         * The length if the receiver's reliable messaging cache length (if a receiver) or how long the cache length on the receiver should be (if a sender).
         */
        protected Integer reliableCache;

        /**
         * Provides documentation about the system's messaging capabilities for this endpoint not otherwise documented by the conformance statement.  For example, process for becoming an authorized messaging exchange partner.
         */
        protected String_ documentation;

        /**
         * Describes the solution's support for an event at this end point.
         */
        protected List<ConformanceMessagingEventComponent> event = new ArrayList<ConformanceMessagingEventComponent>();

      public ConformanceMessagingComponent() {
        super();
      }

        /**
         * @return {@link #endpoint} (The address to which messages and/or replies are to be sent.)
         */
        public Uri getEndpoint() { 
          return this.endpoint;
        }

        /**
         * @param value {@link #endpoint} (The address to which messages and/or replies are to be sent.)
         */
        public ConformanceMessagingComponent setEndpoint(Uri value) { 
          this.endpoint = value;
          return this;
        }

        /**
         * @return The address to which messages and/or replies are to be sent.
         */
        public String getEndpointSimple() { 
          return this.endpoint == null ? null : this.endpoint.getValue();
        }

        /**
         * @param value The address to which messages and/or replies are to be sent.
         */
        public ConformanceMessagingComponent setEndpointSimple(String value) { 
          if (value == null)
            this.endpoint = null;
          else {
            if (this.endpoint == null)
              this.endpoint = new Uri();
            this.endpoint.setValue(value);
          }
          return this;
        }

        /**
         * @return {@link #reliableCache} (The length if the receiver's reliable messaging cache length (if a receiver) or how long the cache length on the receiver should be (if a sender).)
         */
        public Integer getReliableCache() { 
          return this.reliableCache;
        }

        /**
         * @param value {@link #reliableCache} (The length if the receiver's reliable messaging cache length (if a receiver) or how long the cache length on the receiver should be (if a sender).)
         */
        public ConformanceMessagingComponent setReliableCache(Integer value) { 
          this.reliableCache = value;
          return this;
        }

        /**
         * @return The length if the receiver's reliable messaging cache length (if a receiver) or how long the cache length on the receiver should be (if a sender).
         */
        public int getReliableCacheSimple() { 
          return this.reliableCache == null ? null : this.reliableCache.getValue();
        }

        /**
         * @param value The length if the receiver's reliable messaging cache length (if a receiver) or how long the cache length on the receiver should be (if a sender).
         */
        public ConformanceMessagingComponent setReliableCacheSimple(int value) { 
          if (value == -1)
            this.reliableCache = null;
          else {
            if (this.reliableCache == null)
              this.reliableCache = new Integer();
            this.reliableCache.setValue(value);
          }
          return this;
        }

        /**
         * @return {@link #documentation} (Provides documentation about the system's messaging capabilities for this endpoint not otherwise documented by the conformance statement.  For example, process for becoming an authorized messaging exchange partner.)
         */
        public String_ getDocumentation() { 
          return this.documentation;
        }

        /**
         * @param value {@link #documentation} (Provides documentation about the system's messaging capabilities for this endpoint not otherwise documented by the conformance statement.  For example, process for becoming an authorized messaging exchange partner.)
         */
        public ConformanceMessagingComponent setDocumentation(String_ value) { 
          this.documentation = value;
          return this;
        }

        /**
         * @return Provides documentation about the system's messaging capabilities for this endpoint not otherwise documented by the conformance statement.  For example, process for becoming an authorized messaging exchange partner.
         */
        public String getDocumentationSimple() { 
          return this.documentation == null ? null : this.documentation.getValue();
        }

        /**
         * @param value Provides documentation about the system's messaging capabilities for this endpoint not otherwise documented by the conformance statement.  For example, process for becoming an authorized messaging exchange partner.
         */
        public ConformanceMessagingComponent setDocumentationSimple(String value) { 
          if (value == null)
            this.documentation = null;
          else {
            if (this.documentation == null)
              this.documentation = new String_();
            this.documentation.setValue(value);
          }
          return this;
        }

        /**
         * @return {@link #event} (Describes the solution's support for an event at this end point.)
         */
        public List<ConformanceMessagingEventComponent> getEvent() { 
          return this.event;
        }

    // syntactic sugar
        /**
         * @return {@link #event} (Describes the solution's support for an event at this end point.)
         */
        public ConformanceMessagingEventComponent addEvent() { 
          ConformanceMessagingEventComponent t = new ConformanceMessagingEventComponent();
          this.event.add(t);
          return t;
        }

        protected void listChildren(List<Property> childrenList) {
          super.listChildren(childrenList);
          childrenList.add(new Property("endpoint", "uri", "The address to which messages and/or replies are to be sent.", 0, java.lang.Integer.MAX_VALUE, endpoint));
          childrenList.add(new Property("reliableCache", "integer", "The length if the receiver's reliable messaging cache length (if a receiver) or how long the cache length on the receiver should be (if a sender).", 0, java.lang.Integer.MAX_VALUE, reliableCache));
          childrenList.add(new Property("documentation", "string", "Provides documentation about the system's messaging capabilities for this endpoint not otherwise documented by the conformance statement.  For example, process for becoming an authorized messaging exchange partner.", 0, java.lang.Integer.MAX_VALUE, documentation));
          childrenList.add(new Property("event", "", "Describes the solution's support for an event at this end point.", 0, java.lang.Integer.MAX_VALUE, event));
        }

      public ConformanceMessagingComponent copy(Conformance e) {
        ConformanceMessagingComponent dst = new ConformanceMessagingComponent();
        dst.endpoint = endpoint == null ? null : endpoint.copy();
        dst.reliableCache = reliableCache == null ? null : reliableCache.copy();
        dst.documentation = documentation == null ? null : documentation.copy();
        dst.event = new ArrayList<ConformanceMessagingEventComponent>();
        for (ConformanceMessagingEventComponent i : event)
          dst.event.add(i.copy(e));
        return dst;
      }

  }

    public static class ConformanceMessagingEventComponent extends BackboneElement {
        /**
         * Identifies the supported messaging event.
         */
        protected Coding code;

        /**
         * The mode of this event declaration - whether application is sender or receiver.
         */
        protected Enumeration<MessageConformanceEventMode> mode;

        /**
         * Identifies the messaging transport protocol(s) supported by this endpoint.
         */
        protected List<Coding> protocol = new ArrayList<Coding>();

        /**
         * Identifies the resource associated with the event.  This is the resource that defines the event.
         */
        protected Code focus;

        /**
         * Information about the request for this event.
         */
        protected ResourceReference request;

        /**
         * Information about the response for this event.
         */
        protected ResourceReference response;

        /**
         * Guidance on how this event is handled, such as internal system trigger points, business rules, etc.
         */
        protected String_ documentation;

      public ConformanceMessagingEventComponent() {
        super();
      }

      public ConformanceMessagingEventComponent(Coding code, Enumeration<MessageConformanceEventMode> mode, Code focus, ResourceReference request, ResourceReference response) {
        super();
        this.code = code;
        this.mode = mode;
        this.focus = focus;
        this.request = request;
        this.response = response;
      }

        /**
         * @return {@link #code} (Identifies the supported messaging event.)
         */
        public Coding getCode() { 
          return this.code;
        }

        /**
         * @param value {@link #code} (Identifies the supported messaging event.)
         */
        public ConformanceMessagingEventComponent setCode(Coding value) { 
          this.code = value;
          return this;
        }

        /**
         * @return {@link #mode} (The mode of this event declaration - whether application is sender or receiver.)
         */
        public Enumeration<MessageConformanceEventMode> getMode() { 
          return this.mode;
        }

        /**
         * @param value {@link #mode} (The mode of this event declaration - whether application is sender or receiver.)
         */
        public ConformanceMessagingEventComponent setMode(Enumeration<MessageConformanceEventMode> value) { 
          this.mode = value;
          return this;
        }

        /**
         * @return The mode of this event declaration - whether application is sender or receiver.
         */
        public MessageConformanceEventMode getModeSimple() { 
          return this.mode == null ? null : this.mode.getValue();
        }

        /**
         * @param value The mode of this event declaration - whether application is sender or receiver.
         */
        public ConformanceMessagingEventComponent setModeSimple(MessageConformanceEventMode value) { 
            if (this.mode == null)
              this.mode = new Enumeration<MessageConformanceEventMode>();
            this.mode.setValue(value);
          return this;
        }

        /**
         * @return {@link #protocol} (Identifies the messaging transport protocol(s) supported by this endpoint.)
         */
        public List<Coding> getProtocol() { 
          return this.protocol;
        }

    // syntactic sugar
        /**
         * @return {@link #protocol} (Identifies the messaging transport protocol(s) supported by this endpoint.)
         */
        public Coding addProtocol() { 
          Coding t = new Coding();
          this.protocol.add(t);
          return t;
        }

        /**
         * @return {@link #focus} (Identifies the resource associated with the event.  This is the resource that defines the event.)
         */
        public Code getFocus() { 
          return this.focus;
        }

        /**
         * @param value {@link #focus} (Identifies the resource associated with the event.  This is the resource that defines the event.)
         */
        public ConformanceMessagingEventComponent setFocus(Code value) { 
          this.focus = value;
          return this;
        }

        /**
         * @return Identifies the resource associated with the event.  This is the resource that defines the event.
         */
        public String getFocusSimple() { 
          return this.focus == null ? null : this.focus.getValue();
        }

        /**
         * @param value Identifies the resource associated with the event.  This is the resource that defines the event.
         */
        public ConformanceMessagingEventComponent setFocusSimple(String value) { 
            if (this.focus == null)
              this.focus = new Code();
            this.focus.setValue(value);
          return this;
        }

        /**
         * @return {@link #request} (Information about the request for this event.)
         */
        public ResourceReference getRequest() { 
          return this.request;
        }

        /**
         * @param value {@link #request} (Information about the request for this event.)
         */
        public ConformanceMessagingEventComponent setRequest(ResourceReference value) { 
          this.request = value;
          return this;
        }

        /**
         * @return {@link #response} (Information about the response for this event.)
         */
        public ResourceReference getResponse() { 
          return this.response;
        }

        /**
         * @param value {@link #response} (Information about the response for this event.)
         */
        public ConformanceMessagingEventComponent setResponse(ResourceReference value) { 
          this.response = value;
          return this;
        }

        /**
         * @return {@link #documentation} (Guidance on how this event is handled, such as internal system trigger points, business rules, etc.)
         */
        public String_ getDocumentation() { 
          return this.documentation;
        }

        /**
         * @param value {@link #documentation} (Guidance on how this event is handled, such as internal system trigger points, business rules, etc.)
         */
        public ConformanceMessagingEventComponent setDocumentation(String_ value) { 
          this.documentation = value;
          return this;
        }

        /**
         * @return Guidance on how this event is handled, such as internal system trigger points, business rules, etc.
         */
        public String getDocumentationSimple() { 
          return this.documentation == null ? null : this.documentation.getValue();
        }

        /**
         * @param value Guidance on how this event is handled, such as internal system trigger points, business rules, etc.
         */
        public ConformanceMessagingEventComponent setDocumentationSimple(String value) { 
          if (value == null)
            this.documentation = null;
          else {
            if (this.documentation == null)
              this.documentation = new String_();
            this.documentation.setValue(value);
          }
          return this;
        }

        protected void listChildren(List<Property> childrenList) {
          super.listChildren(childrenList);
          childrenList.add(new Property("code", "Coding", "Identifies the supported messaging event.", 0, java.lang.Integer.MAX_VALUE, code));
          childrenList.add(new Property("mode", "code", "The mode of this event declaration - whether application is sender or receiver.", 0, java.lang.Integer.MAX_VALUE, mode));
          childrenList.add(new Property("protocol", "Coding", "Identifies the messaging transport protocol(s) supported by this endpoint.", 0, java.lang.Integer.MAX_VALUE, protocol));
          childrenList.add(new Property("focus", "code", "Identifies the resource associated with the event.  This is the resource that defines the event.", 0, java.lang.Integer.MAX_VALUE, focus));
          childrenList.add(new Property("request", "Resource(Profile)", "Information about the request for this event.", 0, java.lang.Integer.MAX_VALUE, request));
          childrenList.add(new Property("response", "Resource(Profile)", "Information about the response for this event.", 0, java.lang.Integer.MAX_VALUE, response));
          childrenList.add(new Property("documentation", "string", "Guidance on how this event is handled, such as internal system trigger points, business rules, etc.", 0, java.lang.Integer.MAX_VALUE, documentation));
        }

      public ConformanceMessagingEventComponent copy(Conformance e) {
        ConformanceMessagingEventComponent dst = new ConformanceMessagingEventComponent();
        dst.code = code == null ? null : code.copy();
        dst.mode = mode == null ? null : mode.copy();
        dst.protocol = new ArrayList<Coding>();
        for (Coding i : protocol)
          dst.protocol.add(i.copy());
        dst.focus = focus == null ? null : focus.copy();
        dst.request = request == null ? null : request.copy();
        dst.response = response == null ? null : response.copy();
        dst.documentation = documentation == null ? null : documentation.copy();
        return dst;
      }

  }

    public static class ConformanceDocumentComponent extends BackboneElement {
        /**
         * The mode of this event declaration - whether application is sender or receiver.
         */
        protected Enumeration<DocumentMode> mode;

        /**
         * Describes how the application supports or uses the specified document profile.  For example, when are documents created, what action is taken with consumed documents, etc.
         */
        protected String_ documentation;

        /**
         * Constraint on a resource used in the document.
         */
        protected ResourceReference profile;

      public ConformanceDocumentComponent() {
        super();
      }

      public ConformanceDocumentComponent(Enumeration<DocumentMode> mode, ResourceReference profile) {
        super();
        this.mode = mode;
        this.profile = profile;
      }

        /**
         * @return {@link #mode} (The mode of this event declaration - whether application is sender or receiver.)
         */
        public Enumeration<DocumentMode> getMode() { 
          return this.mode;
        }

        /**
         * @param value {@link #mode} (The mode of this event declaration - whether application is sender or receiver.)
         */
        public ConformanceDocumentComponent setMode(Enumeration<DocumentMode> value) { 
          this.mode = value;
          return this;
        }

        /**
         * @return The mode of this event declaration - whether application is sender or receiver.
         */
        public DocumentMode getModeSimple() { 
          return this.mode == null ? null : this.mode.getValue();
        }

        /**
         * @param value The mode of this event declaration - whether application is sender or receiver.
         */
        public ConformanceDocumentComponent setModeSimple(DocumentMode value) { 
            if (this.mode == null)
              this.mode = new Enumeration<DocumentMode>();
            this.mode.setValue(value);
          return this;
        }

        /**
         * @return {@link #documentation} (Describes how the application supports or uses the specified document profile.  For example, when are documents created, what action is taken with consumed documents, etc.)
         */
        public String_ getDocumentation() { 
          return this.documentation;
        }

        /**
         * @param value {@link #documentation} (Describes how the application supports or uses the specified document profile.  For example, when are documents created, what action is taken with consumed documents, etc.)
         */
        public ConformanceDocumentComponent setDocumentation(String_ value) { 
          this.documentation = value;
          return this;
        }

        /**
         * @return Describes how the application supports or uses the specified document profile.  For example, when are documents created, what action is taken with consumed documents, etc.
         */
        public String getDocumentationSimple() { 
          return this.documentation == null ? null : this.documentation.getValue();
        }

        /**
         * @param value Describes how the application supports or uses the specified document profile.  For example, when are documents created, what action is taken with consumed documents, etc.
         */
        public ConformanceDocumentComponent setDocumentationSimple(String value) { 
          if (value == null)
            this.documentation = null;
          else {
            if (this.documentation == null)
              this.documentation = new String_();
            this.documentation.setValue(value);
          }
          return this;
        }

        /**
         * @return {@link #profile} (Constraint on a resource used in the document.)
         */
        public ResourceReference getProfile() { 
          return this.profile;
        }

        /**
         * @param value {@link #profile} (Constraint on a resource used in the document.)
         */
        public ConformanceDocumentComponent setProfile(ResourceReference value) { 
          this.profile = value;
          return this;
        }

        protected void listChildren(List<Property> childrenList) {
          super.listChildren(childrenList);
          childrenList.add(new Property("mode", "code", "The mode of this event declaration - whether application is sender or receiver.", 0, java.lang.Integer.MAX_VALUE, mode));
          childrenList.add(new Property("documentation", "string", "Describes how the application supports or uses the specified document profile.  For example, when are documents created, what action is taken with consumed documents, etc.", 0, java.lang.Integer.MAX_VALUE, documentation));
          childrenList.add(new Property("profile", "Resource(Profile)", "Constraint on a resource used in the document.", 0, java.lang.Integer.MAX_VALUE, profile));
        }

      public ConformanceDocumentComponent copy(Conformance e) {
        ConformanceDocumentComponent dst = new ConformanceDocumentComponent();
        dst.mode = mode == null ? null : mode.copy();
        dst.documentation = documentation == null ? null : documentation.copy();
        dst.profile = profile == null ? null : profile.copy();
        return dst;
      }

  }

    /**
     * The identifier that is used to identify this conformance statement when it is referenced in a specification, model, design or an instance (should be globally unique OID, UUID, or URI).
     */
    protected String_ identifier;

    /**
     * The identifier that is used to identify this version of the conformance statement when it is referenced in a specification, model, design or instance. This is an arbitrary value managed by the profile author manually and the value should be a timestamp.
     */
    protected String_ version;

    /**
     * A free text natural language name identifying the conformance statement.
     */
    protected String_ name;

    /**
     * Name of Organization.
     */
    protected String_ publisher;

    /**
     * Contacts for Organization relevant to this conformance statement.  May be website, email, phone numbers, etc.
     */
    protected List<Contact> telecom = new ArrayList<Contact>();

    /**
     * A free text natural language description of the conformance statement and its use. Typically, this is used when the profile describes a desired rather than an actual solution, for example as a formal expression of requirements as part of an RFP.
     */
    protected String_ description;

    /**
     * The status of this conformance statement.
     */
    protected Enumeration<ConformanceStatementStatus> status;

    /**
     * This conformance statement was authored for testing purposes (or education/evaluation/marketing), and is not intended to be used for genuine usage.
     */
    protected Boolean experimental;

    /**
     * Date that the conformance statement is published.
     */
    protected DateTime date;

    /**
     * Describes the software that is covered by this conformance statement.  Used when the profile describes the capabilities of a particular software version, independent of an installation.
     */
    protected ConformanceSoftwareComponent software;

    /**
     * Used when the statement describes the capabilities of a specific implementation instance - i.e. a particular installation, rather than the capabilities of a software program.
     */
    protected ConformanceImplementationComponent implementation;

    /**
     * The version of the FHIR specification on which this conformance statement is based.
     */
    protected Id fhirVersion;

    /**
     * Whether the application accepts unknown non-"must understand" elements as part of a resource. This does not include extensions, but genuine new additions to a resource.
     */
    protected Boolean acceptUnknown;

    /**
     * The formats supported by this implementation.
     */
    protected List<Code> format = new ArrayList<Code>();

    /**
     * A list of profiles supported by the system. Supported by the system means for a server, that the system hosts/produces a set of resources that conform to a particular profile, that it allows clients to search by this profile, and that these will find the appropriate data. For a client, it means that the client will search by this profile, and process the data following the guidance implicit in the profile.
     */
    protected List<ResourceReference> profile = new ArrayList<ResourceReference>();

    /**
     * Defines the restful capabilities of the solution, if any.
     */
    protected List<ConformanceRestComponent> rest = new ArrayList<ConformanceRestComponent>();

    /**
     * Describes the messaging capabilities of the solution.
     */
    protected List<ConformanceMessagingComponent> messaging = new ArrayList<ConformanceMessagingComponent>();

    /**
     * A document definition.
     */
    protected List<ConformanceDocumentComponent> document = new ArrayList<ConformanceDocumentComponent>();

    public Conformance() {
      super();
    }

    public Conformance(String_ publisher, DateTime date, Id fhirVersion, Boolean acceptUnknown) {
      super();
      this.publisher = publisher;
      this.date = date;
      this.fhirVersion = fhirVersion;
      this.acceptUnknown = acceptUnknown;
    }

    /**
     * @return {@link #identifier} (The identifier that is used to identify this conformance statement when it is referenced in a specification, model, design or an instance (should be globally unique OID, UUID, or URI).)
     */
    public String_ getIdentifier() { 
      return this.identifier;
    }

    /**
     * @param value {@link #identifier} (The identifier that is used to identify this conformance statement when it is referenced in a specification, model, design or an instance (should be globally unique OID, UUID, or URI).)
     */
    public Conformance setIdentifier(String_ value) { 
      this.identifier = value;
      return this;
    }

    /**
     * @return The identifier that is used to identify this conformance statement when it is referenced in a specification, model, design or an instance (should be globally unique OID, UUID, or URI).
     */
    public String getIdentifierSimple() { 
      return this.identifier == null ? null : this.identifier.getValue();
    }

    /**
     * @param value The identifier that is used to identify this conformance statement when it is referenced in a specification, model, design or an instance (should be globally unique OID, UUID, or URI).
     */
    public Conformance setIdentifierSimple(String value) { 
      if (value == null)
        this.identifier = null;
      else {
        if (this.identifier == null)
          this.identifier = new String_();
        this.identifier.setValue(value);
      }
      return this;
    }

    /**
     * @return {@link #version} (The identifier that is used to identify this version of the conformance statement when it is referenced in a specification, model, design or instance. This is an arbitrary value managed by the profile author manually and the value should be a timestamp.)
     */
    public String_ getVersion() { 
      return this.version;
    }

    /**
     * @param value {@link #version} (The identifier that is used to identify this version of the conformance statement when it is referenced in a specification, model, design or instance. This is an arbitrary value managed by the profile author manually and the value should be a timestamp.)
     */
    public Conformance setVersion(String_ value) { 
      this.version = value;
      return this;
    }

    /**
     * @return The identifier that is used to identify this version of the conformance statement when it is referenced in a specification, model, design or instance. This is an arbitrary value managed by the profile author manually and the value should be a timestamp.
     */
    public String getVersionSimple() { 
      return this.version == null ? null : this.version.getValue();
    }

    /**
     * @param value The identifier that is used to identify this version of the conformance statement when it is referenced in a specification, model, design or instance. This is an arbitrary value managed by the profile author manually and the value should be a timestamp.
     */
    public Conformance setVersionSimple(String value) { 
      if (value == null)
        this.version = null;
      else {
        if (this.version == null)
          this.version = new String_();
        this.version.setValue(value);
      }
      return this;
    }

    /**
     * @return {@link #name} (A free text natural language name identifying the conformance statement.)
     */
    public String_ getName() { 
      return this.name;
    }

    /**
     * @param value {@link #name} (A free text natural language name identifying the conformance statement.)
     */
    public Conformance setName(String_ value) { 
      this.name = value;
      return this;
    }

    /**
     * @return A free text natural language name identifying the conformance statement.
     */
    public String getNameSimple() { 
      return this.name == null ? null : this.name.getValue();
    }

    /**
     * @param value A free text natural language name identifying the conformance statement.
     */
    public Conformance setNameSimple(String value) { 
      if (value == null)
        this.name = null;
      else {
        if (this.name == null)
          this.name = new String_();
        this.name.setValue(value);
      }
      return this;
    }

    /**
     * @return {@link #publisher} (Name of Organization.)
     */
    public String_ getPublisher() { 
      return this.publisher;
    }

    /**
     * @param value {@link #publisher} (Name of Organization.)
     */
    public Conformance setPublisher(String_ value) { 
      this.publisher = value;
      return this;
    }

    /**
     * @return Name of Organization.
     */
    public String getPublisherSimple() { 
      return this.publisher == null ? null : this.publisher.getValue();
    }

    /**
     * @param value Name of Organization.
     */
    public Conformance setPublisherSimple(String value) { 
        if (this.publisher == null)
          this.publisher = new String_();
        this.publisher.setValue(value);
      return this;
    }

    /**
     * @return {@link #telecom} (Contacts for Organization relevant to this conformance statement.  May be website, email, phone numbers, etc.)
     */
    public List<Contact> getTelecom() { 
      return this.telecom;
    }

    // syntactic sugar
    /**
     * @return {@link #telecom} (Contacts for Organization relevant to this conformance statement.  May be website, email, phone numbers, etc.)
     */
    public Contact addTelecom() { 
      Contact t = new Contact();
      this.telecom.add(t);
      return t;
    }

    /**
     * @return {@link #description} (A free text natural language description of the conformance statement and its use. Typically, this is used when the profile describes a desired rather than an actual solution, for example as a formal expression of requirements as part of an RFP.)
     */
    public String_ getDescription() { 
      return this.description;
    }

    /**
     * @param value {@link #description} (A free text natural language description of the conformance statement and its use. Typically, this is used when the profile describes a desired rather than an actual solution, for example as a formal expression of requirements as part of an RFP.)
     */
    public Conformance setDescription(String_ value) { 
      this.description = value;
      return this;
    }

    /**
     * @return A free text natural language description of the conformance statement and its use. Typically, this is used when the profile describes a desired rather than an actual solution, for example as a formal expression of requirements as part of an RFP.
     */
    public String getDescriptionSimple() { 
      return this.description == null ? null : this.description.getValue();
    }

    /**
     * @param value A free text natural language description of the conformance statement and its use. Typically, this is used when the profile describes a desired rather than an actual solution, for example as a formal expression of requirements as part of an RFP.
     */
    public Conformance setDescriptionSimple(String value) { 
      if (value == null)
        this.description = null;
      else {
        if (this.description == null)
          this.description = new String_();
        this.description.setValue(value);
      }
      return this;
    }

    /**
     * @return {@link #status} (The status of this conformance statement.)
     */
    public Enumeration<ConformanceStatementStatus> getStatus() { 
      return this.status;
    }

    /**
     * @param value {@link #status} (The status of this conformance statement.)
     */
    public Conformance setStatus(Enumeration<ConformanceStatementStatus> value) { 
      this.status = value;
      return this;
    }

    /**
     * @return The status of this conformance statement.
     */
    public ConformanceStatementStatus getStatusSimple() { 
      return this.status == null ? null : this.status.getValue();
    }

    /**
     * @param value The status of this conformance statement.
     */
    public Conformance setStatusSimple(ConformanceStatementStatus value) { 
      if (value == null)
        this.status = null;
      else {
        if (this.status == null)
          this.status = new Enumeration<ConformanceStatementStatus>();
        this.status.setValue(value);
      }
      return this;
    }

    /**
     * @return {@link #experimental} (This conformance statement was authored for testing purposes (or education/evaluation/marketing), and is not intended to be used for genuine usage.)
     */
    public Boolean getExperimental() { 
      return this.experimental;
    }

    /**
     * @param value {@link #experimental} (This conformance statement was authored for testing purposes (or education/evaluation/marketing), and is not intended to be used for genuine usage.)
     */
    public Conformance setExperimental(Boolean value) { 
      this.experimental = value;
      return this;
    }

    /**
     * @return This conformance statement was authored for testing purposes (or education/evaluation/marketing), and is not intended to be used for genuine usage.
     */
    public boolean getExperimentalSimple() { 
      return this.experimental == null ? null : this.experimental.getValue();
    }

    /**
     * @param value This conformance statement was authored for testing purposes (or education/evaluation/marketing), and is not intended to be used for genuine usage.
     */
    public Conformance setExperimentalSimple(boolean value) { 
      if (value == false)
        this.experimental = null;
      else {
        if (this.experimental == null)
          this.experimental = new Boolean();
        this.experimental.setValue(value);
      }
      return this;
    }

    /**
     * @return {@link #date} (Date that the conformance statement is published.)
     */
    public DateTime getDate() { 
      return this.date;
    }

    /**
     * @param value {@link #date} (Date that the conformance statement is published.)
     */
    public Conformance setDate(DateTime value) { 
      this.date = value;
      return this;
    }

    /**
     * @return Date that the conformance statement is published.
     */
    public String getDateSimple() { 
      return this.date == null ? null : this.date.getValue();
    }

    /**
     * @param value Date that the conformance statement is published.
     */
    public Conformance setDateSimple(String value) { 
        if (this.date == null)
          this.date = new DateTime();
        this.date.setValue(value);
      return this;
    }

    /**
     * @return {@link #software} (Describes the software that is covered by this conformance statement.  Used when the profile describes the capabilities of a particular software version, independent of an installation.)
     */
    public ConformanceSoftwareComponent getSoftware() { 
      return this.software;
    }

    /**
     * @param value {@link #software} (Describes the software that is covered by this conformance statement.  Used when the profile describes the capabilities of a particular software version, independent of an installation.)
     */
    public Conformance setSoftware(ConformanceSoftwareComponent value) { 
      this.software = value;
      return this;
    }

    /**
     * @return {@link #implementation} (Used when the statement describes the capabilities of a specific implementation instance - i.e. a particular installation, rather than the capabilities of a software program.)
     */
    public ConformanceImplementationComponent getImplementation() { 
      return this.implementation;
    }

    /**
     * @param value {@link #implementation} (Used when the statement describes the capabilities of a specific implementation instance - i.e. a particular installation, rather than the capabilities of a software program.)
     */
    public Conformance setImplementation(ConformanceImplementationComponent value) { 
      this.implementation = value;
      return this;
    }

    /**
     * @return {@link #fhirVersion} (The version of the FHIR specification on which this conformance statement is based.)
     */
    public Id getFhirVersion() { 
      return this.fhirVersion;
    }

    /**
     * @param value {@link #fhirVersion} (The version of the FHIR specification on which this conformance statement is based.)
     */
    public Conformance setFhirVersion(Id value) { 
      this.fhirVersion = value;
      return this;
    }

    /**
     * @return The version of the FHIR specification on which this conformance statement is based.
     */
    public String getFhirVersionSimple() { 
      return this.fhirVersion == null ? null : this.fhirVersion.getValue();
    }

    /**
     * @param value The version of the FHIR specification on which this conformance statement is based.
     */
    public Conformance setFhirVersionSimple(String value) { 
        if (this.fhirVersion == null)
          this.fhirVersion = new Id();
        this.fhirVersion.setValue(value);
      return this;
    }

    /**
     * @return {@link #acceptUnknown} (Whether the application accepts unknown non-"must understand" elements as part of a resource. This does not include extensions, but genuine new additions to a resource.)
     */
    public Boolean getAcceptUnknown() { 
      return this.acceptUnknown;
    }

    /**
     * @param value {@link #acceptUnknown} (Whether the application accepts unknown non-"must understand" elements as part of a resource. This does not include extensions, but genuine new additions to a resource.)
     */
    public Conformance setAcceptUnknown(Boolean value) { 
      this.acceptUnknown = value;
      return this;
    }

    /**
     * @return Whether the application accepts unknown non-"must understand" elements as part of a resource. This does not include extensions, but genuine new additions to a resource.
     */
    public boolean getAcceptUnknownSimple() { 
      return this.acceptUnknown == null ? null : this.acceptUnknown.getValue();
    }

    /**
     * @param value Whether the application accepts unknown non-"must understand" elements as part of a resource. This does not include extensions, but genuine new additions to a resource.
     */
    public Conformance setAcceptUnknownSimple(boolean value) { 
        if (this.acceptUnknown == null)
          this.acceptUnknown = new Boolean();
        this.acceptUnknown.setValue(value);
      return this;
    }

    /**
     * @return {@link #format} (The formats supported by this implementation.)
     */
    public List<Code> getFormat() { 
      return this.format;
    }

    // syntactic sugar
    /**
     * @return {@link #format} (The formats supported by this implementation.)
     */
    public Code addFormat() { 
      Code t = new Code();
      this.format.add(t);
      return t;
    }

    /**
     * @param value {@link #format} (The formats supported by this implementation.)
     */
    public Code addFormatSimple(String value) { 
      Code t = new Code();
      t.setValue(value);
      this.format.add(t);
      return t;
    }

    /**
     * @return {@link #profile} (A list of profiles supported by the system. Supported by the system means for a server, that the system hosts/produces a set of resources that conform to a particular profile, that it allows clients to search by this profile, and that these will find the appropriate data. For a client, it means that the client will search by this profile, and process the data following the guidance implicit in the profile.)
     */
    public List<ResourceReference> getProfile() { 
      return this.profile;
    }

    // syntactic sugar
    /**
     * @return {@link #profile} (A list of profiles supported by the system. Supported by the system means for a server, that the system hosts/produces a set of resources that conform to a particular profile, that it allows clients to search by this profile, and that these will find the appropriate data. For a client, it means that the client will search by this profile, and process the data following the guidance implicit in the profile.)
     */
    public ResourceReference addProfile() { 
      ResourceReference t = new ResourceReference();
      this.profile.add(t);
      return t;
    }

    /**
     * @return {@link #rest} (Defines the restful capabilities of the solution, if any.)
     */
    public List<ConformanceRestComponent> getRest() { 
      return this.rest;
    }

    // syntactic sugar
    /**
     * @return {@link #rest} (Defines the restful capabilities of the solution, if any.)
     */
    public ConformanceRestComponent addRest() { 
      ConformanceRestComponent t = new ConformanceRestComponent();
      this.rest.add(t);
      return t;
    }

    /**
     * @return {@link #messaging} (Describes the messaging capabilities of the solution.)
     */
    public List<ConformanceMessagingComponent> getMessaging() { 
      return this.messaging;
    }

    // syntactic sugar
    /**
     * @return {@link #messaging} (Describes the messaging capabilities of the solution.)
     */
    public ConformanceMessagingComponent addMessaging() { 
      ConformanceMessagingComponent t = new ConformanceMessagingComponent();
      this.messaging.add(t);
      return t;
    }

    /**
     * @return {@link #document} (A document definition.)
     */
    public List<ConformanceDocumentComponent> getDocument() { 
      return this.document;
    }

    // syntactic sugar
    /**
     * @return {@link #document} (A document definition.)
     */
    public ConformanceDocumentComponent addDocument() { 
      ConformanceDocumentComponent t = new ConformanceDocumentComponent();
      this.document.add(t);
      return t;
    }

      protected void listChildren(List<Property> childrenList) {
        super.listChildren(childrenList);
        childrenList.add(new Property("identifier", "string", "The identifier that is used to identify this conformance statement when it is referenced in a specification, model, design or an instance (should be globally unique OID, UUID, or URI).", 0, java.lang.Integer.MAX_VALUE, identifier));
        childrenList.add(new Property("version", "string", "The identifier that is used to identify this version of the conformance statement when it is referenced in a specification, model, design or instance. This is an arbitrary value managed by the profile author manually and the value should be a timestamp.", 0, java.lang.Integer.MAX_VALUE, version));
        childrenList.add(new Property("name", "string", "A free text natural language name identifying the conformance statement.", 0, java.lang.Integer.MAX_VALUE, name));
        childrenList.add(new Property("publisher", "string", "Name of Organization.", 0, java.lang.Integer.MAX_VALUE, publisher));
        childrenList.add(new Property("telecom", "Contact", "Contacts for Organization relevant to this conformance statement.  May be website, email, phone numbers, etc.", 0, java.lang.Integer.MAX_VALUE, telecom));
        childrenList.add(new Property("description", "string", "A free text natural language description of the conformance statement and its use. Typically, this is used when the profile describes a desired rather than an actual solution, for example as a formal expression of requirements as part of an RFP.", 0, java.lang.Integer.MAX_VALUE, description));
        childrenList.add(new Property("status", "code", "The status of this conformance statement.", 0, java.lang.Integer.MAX_VALUE, status));
        childrenList.add(new Property("experimental", "boolean", "This conformance statement was authored for testing purposes (or education/evaluation/marketing), and is not intended to be used for genuine usage.", 0, java.lang.Integer.MAX_VALUE, experimental));
        childrenList.add(new Property("date", "dateTime", "Date that the conformance statement is published.", 0, java.lang.Integer.MAX_VALUE, date));
        childrenList.add(new Property("software", "", "Describes the software that is covered by this conformance statement.  Used when the profile describes the capabilities of a particular software version, independent of an installation.", 0, java.lang.Integer.MAX_VALUE, software));
        childrenList.add(new Property("implementation", "", "Used when the statement describes the capabilities of a specific implementation instance - i.e. a particular installation, rather than the capabilities of a software program.", 0, java.lang.Integer.MAX_VALUE, implementation));
        childrenList.add(new Property("fhirVersion", "id", "The version of the FHIR specification on which this conformance statement is based.", 0, java.lang.Integer.MAX_VALUE, fhirVersion));
        childrenList.add(new Property("acceptUnknown", "boolean", "Whether the application accepts unknown non-'must understand' elements as part of a resource. This does not include extensions, but genuine new additions to a resource.", 0, java.lang.Integer.MAX_VALUE, acceptUnknown));
        childrenList.add(new Property("format", "code", "The formats supported by this implementation.", 0, java.lang.Integer.MAX_VALUE, format));
        childrenList.add(new Property("profile", "Resource(Profile)", "A list of profiles supported by the system. Supported by the system means for a server, that the system hosts/produces a set of resources that conform to a particular profile, that it allows clients to search by this profile, and that these will find the appropriate data. For a client, it means that the client will search by this profile, and process the data following the guidance implicit in the profile.", 0, java.lang.Integer.MAX_VALUE, profile));
        childrenList.add(new Property("rest", "", "Defines the restful capabilities of the solution, if any.", 0, java.lang.Integer.MAX_VALUE, rest));
        childrenList.add(new Property("messaging", "", "Describes the messaging capabilities of the solution.", 0, java.lang.Integer.MAX_VALUE, messaging));
        childrenList.add(new Property("document", "", "A document definition.", 0, java.lang.Integer.MAX_VALUE, document));
      }

      public Conformance copy() {
        Conformance dst = new Conformance();
        dst.identifier = identifier == null ? null : identifier.copy();
        dst.version = version == null ? null : version.copy();
        dst.name = name == null ? null : name.copy();
        dst.publisher = publisher == null ? null : publisher.copy();
        dst.telecom = new ArrayList<Contact>();
        for (Contact i : telecom)
          dst.telecom.add(i.copy());
        dst.description = description == null ? null : description.copy();
        dst.status = status == null ? null : status.copy();
        dst.experimental = experimental == null ? null : experimental.copy();
        dst.date = date == null ? null : date.copy();
        dst.software = software == null ? null : software.copy(dst);
        dst.implementation = implementation == null ? null : implementation.copy(dst);
        dst.fhirVersion = fhirVersion == null ? null : fhirVersion.copy();
        dst.acceptUnknown = acceptUnknown == null ? null : acceptUnknown.copy();
        dst.format = new ArrayList<Code>();
        for (Code i : format)
          dst.format.add(i.copy());
        dst.profile = new ArrayList<ResourceReference>();
        for (ResourceReference i : profile)
          dst.profile.add(i.copy());
        dst.rest = new ArrayList<ConformanceRestComponent>();
        for (ConformanceRestComponent i : rest)
          dst.rest.add(i.copy(dst));
        dst.messaging = new ArrayList<ConformanceMessagingComponent>();
        for (ConformanceMessagingComponent i : messaging)
          dst.messaging.add(i.copy(dst));
        dst.document = new ArrayList<ConformanceDocumentComponent>();
        for (ConformanceDocumentComponent i : document)
          dst.document.add(i.copy(dst));
        return dst;
      }

      protected Conformance typedCopy() {
        return copy();
      }

  @Override
  public ResourceType getResourceType() {
    return ResourceType.Conformance;
   }


}

