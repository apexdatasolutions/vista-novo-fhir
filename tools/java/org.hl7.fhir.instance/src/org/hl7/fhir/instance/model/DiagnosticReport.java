package org.hl7.fhir.instance.model;

/*
  Copyright (c) 2011-2012, HL7, Inc.
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

// Generated on Mon, Mar 4, 2013 20:03+1100 for FHIR v0.07

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * The findings and interpretation of diagnostic  tests performed on patients and/or specimens. The report includes clinical context such as requesting and provider information, and some mix of atomic results, images, textual and coded interpretation, and formatted representation of diagnostic reports
 */
public class DiagnosticReport extends Resource {

    public enum ObservationStatus {
        registered, // The existence of the observation is registered, but there is no result yet available
        interim, // This is an initial or interim observation: data may be incomplete or unverified
        final_, // The observation is complete and verified by an authorised person
        amended, // The observation has been modified subsequent to being Final, and is complete and verified by an authorised person
        cancelled, // The observation is unavailable because the measurement was not started or not completed (also sometimes called "aborted")
        withdrawn, // The observation has been withdrawn following previous Final release
        Null; // added to help the parsers
        public static ObservationStatus fromCode(String codeString) throws Exception {
            if (codeString == null || "".equals(codeString))
                return null;
        if ("registered".equals(codeString))
          return registered;
        if ("interim".equals(codeString))
          return interim;
        if ("final".equals(codeString))
          return final_;
        if ("amended".equals(codeString))
          return amended;
        if ("cancelled".equals(codeString))
          return cancelled;
        if ("withdrawn".equals(codeString))
          return withdrawn;
        throw new Exception("Unknown ObservationStatus code '"+codeString+"'");
        }
        public String toCode() {
          switch (this) {
            case registered: return "registered";
            case interim: return "interim";
            case final_: return "final";
            case amended: return "amended";
            case cancelled: return "cancelled";
            case withdrawn: return "withdrawn";
            default: return "?";
          }
        }
    }

  public class ObservationStatusEnumFactory implements EnumFactory {
    public Enum<?> fromCode(String codeString) throws Exception {
      if (codeString == null || "".equals(codeString))
            if (codeString == null || "".equals(codeString))
                return null;
        if ("registered".equals(codeString))
          return ObservationStatus.registered;
        if ("interim".equals(codeString))
          return ObservationStatus.interim;
        if ("final".equals(codeString))
          return ObservationStatus.final_;
        if ("amended".equals(codeString))
          return ObservationStatus.amended;
        if ("cancelled".equals(codeString))
          return ObservationStatus.cancelled;
        if ("withdrawn".equals(codeString))
          return ObservationStatus.withdrawn;
        throw new Exception("Unknown ObservationStatus code '"+codeString+"'");
        }
    public String toCode(Enum<?> code) throws Exception {
      if (code == ObservationStatus.registered)
        return "registered";
      if (code == ObservationStatus.interim)
        return "interim";
      if (code == ObservationStatus.final_)
        return "final";
      if (code == ObservationStatus.amended)
        return "amended";
      if (code == ObservationStatus.cancelled)
        return "cancelled";
      if (code == ObservationStatus.withdrawn)
        return "withdrawn";
      return "?";
      }
    }

    public class RequestDetail extends Element {
        /**
         * The encounter that this diagnostic investigation is associated with
         */
        private ResourceReference encounter;

        /**
         * The local ID assigned to the order by the order requester.
         */
        private Identifier requestOrderId;

        /**
         * The local ID assigned to the test order by the diagnostic service provider
         */
        private Identifier receiverOrderId;

        /**
         * Identification of pathology test requested,
         */
        private List<CodeableConcept> requestTest = new ArrayList<CodeableConcept>();

        /**
         * Anatomical location where the request test should be performed
         */
        private CodeableConcept bodySite;

        /**
         * Details of the clinician or organisation requesting the diagnostic service
         */
        private ResourceReference requester;

        /**
         * Details of the clinical information provided to the diagnostic service along with the original request
         */
        private String_ clinicalInfo;

        public ResourceReference getEncounter() { 
          return this.encounter;
        }

        public void setEncounter(ResourceReference value) { 
          this.encounter = value;
        }

        public Identifier getRequestOrderId() { 
          return this.requestOrderId;
        }

        public void setRequestOrderId(Identifier value) { 
          this.requestOrderId = value;
        }

        public Identifier getReceiverOrderId() { 
          return this.receiverOrderId;
        }

        public void setReceiverOrderId(Identifier value) { 
          this.receiverOrderId = value;
        }

        public List<CodeableConcept> getRequestTest() { 
          return this.requestTest;
        }

        public CodeableConcept getBodySite() { 
          return this.bodySite;
        }

        public void setBodySite(CodeableConcept value) { 
          this.bodySite = value;
        }

        public ResourceReference getRequester() { 
          return this.requester;
        }

        public void setRequester(ResourceReference value) { 
          this.requester = value;
        }

        public String_ getClinicalInfo() { 
          return this.clinicalInfo;
        }

        public void setClinicalInfo(String_ value) { 
          this.clinicalInfo = value;
        }

        public String getClinicalInfoSimple() { 
          return this.clinicalInfo == null ? null : this.clinicalInfo.getValue();
        }

        public void setClinicalInfoSimple(String value) { 
          if (value == null)
            this.clinicalInfo = null;
          else {
            if (this.clinicalInfo == null)
              this.clinicalInfo = new String_();
            this.clinicalInfo.setValue(value);
          }
        }

  }

    public class Results extends Element {
        /**
         * A code or name that describes this group of results. For the base group, this is the report name
         */
        private CodeableConcept name;

        /**
         * Details about the individual specimen to which these "Result group" test results refer
         */
        private ResourceReference specimen;

        /**
         * A sub-group in a report group. Sub groups can be grouped in arbitrary ways. The group.name defines the purpose and interpretation of the grouping
         */
        private List<Results> group = new ArrayList<Results>();

        /**
         * Specific detailed result, including both the value of the result item and additional information that may be useful for clinical interpretation. Results include whatever specific data items pathology labs report as part of the clinical service; it is not confined to measurements.
         */
        private List<ResourceReference> result = new ArrayList<ResourceReference>();

        public CodeableConcept getName() { 
          return this.name;
        }

        public void setName(CodeableConcept value) { 
          this.name = value;
        }

        public ResourceReference getSpecimen() { 
          return this.specimen;
        }

        public void setSpecimen(ResourceReference value) { 
          this.specimen = value;
        }

        public List<Results> getGroup() { 
          return this.group;
        }

        public List<ResourceReference> getResult() { 
          return this.result;
        }

  }

    /**
     * The status of the diagnostic report as a whole
     */
    private Enumeration<ObservationStatus> status;

    /**
     * The date and/or time that this version of the report was released from the source diagnostic service
     */
    private Instant issued;

    /**
     * The subject of the report. Usually, but not always, this is a patient. However diagnostic services also perform analyses on specimens collected from a variety of other sources
     */
    private ResourceReference subject;

    /**
     * The diagnostic service that is responsible for issuing the report
     */
    private ResourceReference performer;

    /**
     * The local ID assigned to the report by the order filler, usually by the Information System of the diagnostic service provider
     */
    private Identifier reportId;

    /**
     * Details concerning a single pathology test requested.
     */
    private List<RequestDetail> requestDetail = new ArrayList<RequestDetail>();

    /**
     * The section of the diagnostic service that performs the examination e.g. biochemistry, haematology, MRI
     */
    private CodeableConcept serviceCategory;

    /**
     * The diagnostically relevant time for this report
     */
    private DateTime diagnosticTime;

    /**
     * A group of results. Results may be grouped by specimen, or by some value in DiagnosticReport.resultGroup.name to describe what binds all the results together.
     */
    private Results results;

    /**
     * A list of key images associated with this report. The images are generally created during the diagnostic process, and maybe directly of the patient, or of treated specimens (i.e. slides of interest)
     */
    private List<ResourceReference> image = new ArrayList<ResourceReference>();

    /**
     * Concise and clinically contextualised narrative interpretation of the diagnostic report
     */
    private String_ conclusion;

    /**
     * Codes for the conclusion
     */
    private List<CodeableConcept> codedDiagnosis = new ArrayList<CodeableConcept>();

    /**
     * Rich text representation of the entire result as issued by the diagnostic service. Multiple formats are allowed but they must be semantically equivalent.
     */
    private List<Attachment> representation = new ArrayList<Attachment>();

    public Enumeration<ObservationStatus> getStatus() { 
      return this.status;
    }

    public void setStatus(Enumeration<ObservationStatus> value) { 
      this.status = value;
    }

    public ObservationStatus getStatusSimple() { 
      return this.status == null ? null : this.status.getValue();
    }

    public void setStatusSimple(ObservationStatus value) { 
      if (value == null)
        this.status = null;
      else {
        if (this.status == null)
          this.status = new Enumeration<ObservationStatus>();
        this.status.setValue(value);
      }
    }

    public Instant getIssued() { 
      return this.issued;
    }

    public void setIssued(Instant value) { 
      this.issued = value;
    }

    public Calendar getIssuedSimple() { 
      return this.issued == null ? null : this.issued.getValue();
    }

    public void setIssuedSimple(Calendar value) { 
      if (value == null)
        this.issued = null;
      else {
        if (this.issued == null)
          this.issued = new Instant();
        this.issued.setValue(value);
      }
    }

    public ResourceReference getSubject() { 
      return this.subject;
    }

    public void setSubject(ResourceReference value) { 
      this.subject = value;
    }

    public ResourceReference getPerformer() { 
      return this.performer;
    }

    public void setPerformer(ResourceReference value) { 
      this.performer = value;
    }

    public Identifier getReportId() { 
      return this.reportId;
    }

    public void setReportId(Identifier value) { 
      this.reportId = value;
    }

    public List<RequestDetail> getRequestDetail() { 
      return this.requestDetail;
    }

    public CodeableConcept getServiceCategory() { 
      return this.serviceCategory;
    }

    public void setServiceCategory(CodeableConcept value) { 
      this.serviceCategory = value;
    }

    public DateTime getDiagnosticTime() { 
      return this.diagnosticTime;
    }

    public void setDiagnosticTime(DateTime value) { 
      this.diagnosticTime = value;
    }

    public String getDiagnosticTimeSimple() { 
      return this.diagnosticTime == null ? null : this.diagnosticTime.getValue();
    }

    public void setDiagnosticTimeSimple(String value) { 
      if (value == null)
        this.diagnosticTime = null;
      else {
        if (this.diagnosticTime == null)
          this.diagnosticTime = new DateTime();
        this.diagnosticTime.setValue(value);
      }
    }

    public Results getResults() { 
      return this.results;
    }

    public void setResults(Results value) { 
      this.results = value;
    }

    public List<ResourceReference> getImage() { 
      return this.image;
    }

    public String_ getConclusion() { 
      return this.conclusion;
    }

    public void setConclusion(String_ value) { 
      this.conclusion = value;
    }

    public String getConclusionSimple() { 
      return this.conclusion == null ? null : this.conclusion.getValue();
    }

    public void setConclusionSimple(String value) { 
      if (value == null)
        this.conclusion = null;
      else {
        if (this.conclusion == null)
          this.conclusion = new String_();
        this.conclusion.setValue(value);
      }
    }

    public List<CodeableConcept> getCodedDiagnosis() { 
      return this.codedDiagnosis;
    }

    public List<Attachment> getRepresentation() { 
      return this.representation;
    }

  @Override
  public ResourceType getResourceType() {
    return ResourceType.DiagnosticReport;
   }


}

