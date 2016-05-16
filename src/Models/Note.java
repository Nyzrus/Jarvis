package Models;

import java.util.Objects;

public class Note {

	private String header;
	private String content;
	
	protected Note(Builder builder){
		this.header = Objects.requireNonNull(builder.header);
		this.content = Objects.requireNonNull(builder.content);
	}
	
	public String getHeader() {
		return header;
	}

	public String getContent() {
		return content;
	}

	public static class Builder{
		private String header;
		private String content;
		
		public Builder setHeader(String header){
			this.header = header;
			return this;
		}
		
		public Builder setContent(String content){
			this.content = content;
			return this;
		}
		
		public Note build(){
			return new Note(this);
		}
		
	}
}
