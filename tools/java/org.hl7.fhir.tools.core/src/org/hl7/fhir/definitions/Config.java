package org.hl7.fhir.definitions;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class Config {

  public static final String VERSION_REGEX = "v[0-9]\\.[0-9][0-9]";
  
  public static final boolean SUPPRESS_WRAPPER_ELEMENTS = true;
  public static final boolean SHOW_SINGLE_CARDINALITIES = false;
  public static final SimpleDateFormat DATE_FORMAT() {
    return new SimpleDateFormat("EEE, MMM d, yyyy HH:mmZ", new Locale("en", "US"));
  }

  public static final String FULL_LICENSE_CODE = 
      
"  Copyright (c) 2011-2012, HL7, Inc\r\n"+
"  All rights reserved.\r\n"+
"  \r\n"+
"  Redistribution and use in source and binary forms, with or without modification, \r\n" +
"  are permitted provided that the following conditions are met:\r\n"+
"  \r\n"+
"   * Redistributions of source code must retain the above copyright notice, this \r\n" +
"     list of conditions and the following disclaimer.\r\n"+
"   * Redistributions in binary form must reproduce the above copyright notice, \r\n" +
"     this list of conditions and the following disclaimer in the documentation \r\n" +
"     and/or other materials provided with the distribution.\r\n"+
"   * Neither the name of HL7 nor the names of its contributors may be used to \r\n"+
"     endorse or promote products derived from this software without specific \r\n"+
"     prior written permission.\r\n"+
"  \r\n"+
"  THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS \"AS IS\" AND \r\n" +
"  ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED \r\n" +
"  WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. \r\n" +
"  IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, \r\n" +
"  INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT \r\n" +
"  NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR \r\n" +
"  PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, \r\n" +
"  WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) \r\n" +
"  ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE \r\n" +
"  POSSIBILITY OF SUCH DAMAGE.\r\n"+
"  \r\n";
      
}
