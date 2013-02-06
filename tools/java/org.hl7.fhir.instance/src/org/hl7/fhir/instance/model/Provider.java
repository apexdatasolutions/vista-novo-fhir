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

// Generated on Sat, Feb 2, 2013 11:50+1100 for FHIR v0.07

import java.util.*;

/**
 * A person who represents an organization, and is authorized to perform actions on its behalf
 */
public class Provider extends Resource {

    /**
     * An identifier that applies to this person in this role
     */
    private List<Identifier> identifier = new ArrayList<Identifier>();

    /**
     * Provider Demographic details
     */
    private Demographics details;

    /**
     * The organisation that is being represented
     */
    private ResourceReference organization;

    /**
     * The way in which the person represents the organisation - what role do they have?
     */
    private List<CodeableConcept> role = new ArrayList<CodeableConcept>();

    /**
     * Represents a specific specialty within the healthcare facility under which the agent acts
     */
    private List<CodeableConcept> specialty = new ArrayList<CodeableConcept>();

    /**
     * The time period during which the agent was/is authorised to represent the organisation.
     */
    private Period period;

    /**
     * A postal address for this person playing this role
     */
    private List<Address> address = new ArrayList<Address>();

    /**
     * A contact detail address for this person playing this role
     */
    private List<Contact> contact = new ArrayList<Contact>();

    public List<Identifier> getIdentifier() { 
      return this.identifier;
    }

    public Demographics getDetails() { 
      return this.details;
    }

    public void setDetails(Demographics value) { 
      this.details = value;
    }

    public ResourceReference getOrganization() { 
      return this.organization;
    }

    public void setOrganization(ResourceReference value) { 
      this.organization = value;
    }

    public List<CodeableConcept> getRole() { 
      return this.role;
    }

    public List<CodeableConcept> getSpecialty() { 
      return this.specialty;
    }

    public Period getPeriod() { 
      return this.period;
    }

    public void setPeriod(Period value) { 
      this.period = value;
    }

    public List<Address> getAddress() { 
      return this.address;
    }

    public List<Contact> getContact() { 
      return this.contact;
    }

  @Override
  public ResourceType getResourceType() {
    return ResourceType.Provider;
   }


}

