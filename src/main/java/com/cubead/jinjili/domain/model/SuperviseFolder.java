package com.cubead.jinjili.domain.model;

import java.util.List;

public class SuperviseFolder {
	private String folderId;
	private String folderName;
	private List<SuperviseMonitor> monitors;
	
	public String getFolderId() {
		return folderId;
	}
	public void setFolderId(String folderId) {
		this.folderId = folderId;
	}
	public String getFolderName() {
		return folderName;
	}
	public void setFolderName(String folderName) {
		this.folderName = folderName;
	}
	public List<SuperviseMonitor> getMonitors() {
		return monitors;
	}
	public void setMonitors(List<SuperviseMonitor> monitors) {
		this.monitors = monitors;
	}
}
