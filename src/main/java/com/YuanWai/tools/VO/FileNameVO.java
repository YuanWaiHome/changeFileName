package com.YuanWai.tools.VO;


public class FileNameVO {
	private String folderUrl; /* 資料夾位置 */
	private String fileName; /* 檔案名稱 */
	private String formatName; /* 修改後名稱 */

	public FileNameVO() {
	}

	public String getFolderUrl() {
		return folderUrl;
	}

	public void setFolderUrl(String folderUrl) {
		this.folderUrl = folderUrl;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFormatName() {
		return formatName;
	}

	public void setFormatName(String formatName) {
		this.formatName = formatName;
	}

	private FileNameVO(Builder builder) {
		this.folderUrl = builder.folderUrl;
		this.fileName = builder.fileName;
		this.formatName = builder.formatName;
	}

	public static Builder builder() {
		return new Builder();
	}

	public static final class Builder {
		private String folderUrl;
		private String fileName;
		private String formatName;

		private Builder() {
		}

		public Builder withFolderUrl(String folderUrl) {
			this.folderUrl = folderUrl;
			return this;
		}

		public Builder withFileName(String fileName) {
			this.fileName = fileName;
			return this;
		}

		public Builder withFormatName(String formatName) {
			this.formatName = formatName;
			return this;
		}

		public FileNameVO build() {
			return new FileNameVO(this);
		}
	}

}
