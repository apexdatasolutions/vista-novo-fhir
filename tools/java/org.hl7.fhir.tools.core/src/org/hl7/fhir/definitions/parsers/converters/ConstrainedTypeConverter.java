package org.hl7.fhir.definitions.parsers.converters;

/*
Copyright (c) 2011-2012, HL7, Inc
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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.hl7.fhir.definitions.ecore.fhir.ConstrainedTypeDefn;
import org.hl7.fhir.definitions.ecore.fhir.FhirFactory;
import org.hl7.fhir.definitions.ecore.fhir.TypeRef;


public class ConstrainedTypeConverter 
{
	public static List<ConstrainedTypeDefn> buildConstrainedTypesFromFhirModel( 
			Collection<org.hl7.fhir.definitions.model.DefinedCode> constrainedTypes,
			Map<String,org.hl7.fhir.definitions.model.Invariant> invariants )
				throws Exception
	{
		List<ConstrainedTypeDefn> result = new ArrayList<ConstrainedTypeDefn>();
		
	    for (org.hl7.fhir.definitions.model.DefinedCode constrainedType : constrainedTypes) 
	    {
	    	org.hl7.fhir.definitions.model.Invariant inv = 
	    			invariants.get(constrainedType.getCode());
	    	
	    	if( inv == null )
	    		throw new Exception( "Invariants missing for constrained type" + constrainedType.getCode());
	    	
	    	result.add(buildConstrainedTypeFromFhirModel(constrainedType, inv));
	    }
	    
	    return result;
	}
	
	public static ConstrainedTypeDefn buildConstrainedTypeFromFhirModel( 
			org.hl7.fhir.definitions.model.DefinedCode constrainedType,
			org.hl7.fhir.definitions.model.Invariant invariant) throws Exception
	{
		ConstrainedTypeDefn result = FhirFactory.eINSTANCE.createConstrainedTypeDefn();
		
		// Since the comment in the old Fhir model will not contain multiple types
		// and none of them is a Resource(A|B|C) ref, we can be sure the buildTypes..()
		// will only return 1 result;
		TypeRef baseType = 
				TypeRefConverter.buildTypeRefsFromFhirTypeName(constrainedType.getComment()).get(0);
		
		result.setBaseType( baseType );
		result.setName( constrainedType.getCode() );
		
		//TODO: This could be multiple invariants, but current Fhir model only allows 1.
		result.getDetails().add( 
				CompositeTypeConverter.buildInvariantFromFhirModel(invariant) );
		
		return result;
	}
}