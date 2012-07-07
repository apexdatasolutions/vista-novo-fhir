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

// Generated on Sat, Jul 7, 2012 09:49+1000 for FHIR v0.04

import java.util.*;

/**
 * A name of a human, or a name given to an animal by a human.
 */
public class HumanName extends Type {

    public enum NameUse {
        usual, // Known as/conventional/the one you normally use
        official, // The formal name as registered in an official (government) registry, but which name might not be commonly used. May be called "legal name".
        temp, // A temporary name. A name valid time can provide more detailed information. This may also be used for temporary names assigned at birth or in emergency situations.
        anonymous, // Anonymous assigned name, alias, or pseudonym (used to protect a person's identity for privacy reasons)
        old, // This name is no longer in use (or was never correct, but retained for records)
        maiden; // A name used prior to marriage. Marriage naming customs vary greatly around the world. This name use is for use by applications that collect and store "maiden" names. Though the concept of maiden name is often gender specific, the use of this term is not gender specific. The use of this term does not imply any particular history for a person‘s name, nor should the maiden name be determined algorithmically
        public static NameUse fromCode(String codeString) throws Exception {
            if (codeString == null || "".equals(codeString))
                return null;
        if ("usual".equals(codeString))
          return usual;
        if ("official".equals(codeString))
          return official;
        if ("temp".equals(codeString))
          return temp;
        if ("anonymous".equals(codeString))
          return anonymous;
        if ("old".equals(codeString))
          return old;
        if ("maiden".equals(codeString))
          return maiden;
        throw new Exception("Unknown NameUse code '"+codeString+"'");
        }
        public String toCode() {
          switch (this) {
            case usual: return "usual";
            case official: return "official";
            case temp: return "temp";
            case anonymous: return "anonymous";
            case old: return "old";
            case maiden: return "maiden";
            default: return "?";
          }
        }
    }

    public enum NamePartType {
        family, // Family name, this is the name that links to the genealogy. In some cultures (e.g. Eritrea) the family name of a son is the first name of his father.
        given, // Given name. NOTE Not to be called "first name" since given names do not always come first. .
        title; // Part of the name that is acquired as a title due to academic, legal, employment or nobility status etc. NOTE Title name parts include name parts that come after the name, such as qualifications. 
        public static NamePartType fromCode(String codeString) throws Exception {
            if (codeString == null || "".equals(codeString))
                return null;
        if ("family".equals(codeString))
          return family;
        if ("given".equals(codeString))
          return given;
        if ("title".equals(codeString))
          return title;
        throw new Exception("Unknown NamePartType code '"+codeString+"'");
        }
        public String toCode() {
          switch (this) {
            case family: return "family";
            case given: return "given";
            case title: return "title";
            default: return "?";
          }
        }
    }

    public class Part extends Element {
        /**
         * Type of name part
         */
        private NamePartType type;

        /**
         * The content of the name part
         */
        private String value;

        public NamePartType getType() { 
          return this.type;
        }

        public void setType(NamePartType value) { 
          this.type = value;
        }

        public String getValue() { 
          return this.value;
        }

        public void setValue(String value) { 
          this.value = value;
        }

    }

    /**
     * The use of this name
     */
    private NameUse use;

    /**
     * a full text representation of the name
     */
    private String text;

    /**
     * A part of a name
     */
    private List<Part> part = new ArrayList<Part>();

    /**
     * Time period when name was/is in use
     */
    private Period period;

    public NameUse getUse() { 
      return this.use;
    }

    public void setUse(NameUse value) { 
      this.use = value;
    }

    public String getText() { 
      return this.text;
    }

    public void setText(String value) { 
      this.text = value;
    }

    public List<Part> getPart() { 
      return this.part;
    }

    public Period getPeriod() { 
      return this.period;
    }

    public void setPeriod(Period value) { 
      this.period = value;
    }


}

