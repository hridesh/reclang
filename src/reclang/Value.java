package reclang;

import java.util.List;

import reclang.AST.Exp;

public interface Value {
	public String tostring();
	static class Fun implements Value { //New in the funclang
		private Env _env;
		private List<String> _formals;
		private Exp _body;
		public Fun(Env env, List<String> formals, Exp body) {
			_env = env;
			_formals = formals;
			_body = body;
		}
		public Env env() { return _env; }
		public List<String> formals() { return _formals; }
		public Exp body() { return _body; }
	    public String tostring() { 
			String result = "(lambda ( ";
			for(String formal : _formals) 
				result += formal + " ";
			result += ") ";
			result += _body.accept(new Printer.ExpToStringConverter(), _env);
			return result + ")";
	    }
	}
	static class Int implements Value {
		private int _val;
	    public Int(int v) { _val = v; } 
	    public int v() { return _val; }
	    public String tostring() { return "" + _val; }
	}
	static class Bool implements Value {
		private boolean _val;
	    public Bool(boolean v) { _val = v; } 
	    public boolean v() { return _val; }
	    public String tostring() { return "" + _val; }
	}
	static class Unit implements Value {
		public static final Unit v = new Unit();
	    public String tostring() { return "unit"; }
	}
	static class DynamicError implements Value { 
		private String message = "Unknown dynamic error.";
		public DynamicError() { }
		public DynamicError(String message) { this.message = message; }
	    public String tostring() { return "" + message; }
	}
}
