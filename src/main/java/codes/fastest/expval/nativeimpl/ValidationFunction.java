package codes.fastest.expval.nativeimpl;

import java.util.List;

import codes.fastest.core.validator.FieldResult;

public interface ValidationFunction {
	
	public String msg = "Field: [%s] with value [%s] does not comply with rule: %s";
	
	public FieldResult validate(ValidationAttempt context);
	
	public List<String> getParams();
	
}
