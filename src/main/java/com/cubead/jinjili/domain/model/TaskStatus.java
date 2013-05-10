package com.cubead.jinjili.domain.model;


public enum TaskStatus{
		/**
		 * 将下载
		 */
		TODO("1"),
		/**
		 * 下载中
		 */
		DOWNLOADING("3"),
		
		/**
		 * 下载请求发出并得到fileid
		 */
		IDDONE("5"),
		
		
		/**
		 * 失败
		 */
		FAIL("7"),

		/**
		 * 已完成
		 */
		DOWNLODED("9"),
		
		/**
		 * 索引中
		 */
		INDEXING("11"),
		
		/**
		 * 已完成
		 */
		DONE("15");
		
		
		
		
		private String value;
		
		private TaskStatus(String value){
			this.value = value;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}
		
		public String getName(){
			return this.name();
		}
		
	}