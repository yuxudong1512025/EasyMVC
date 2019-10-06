package com.support.core.resource;


import com.publicgroup.resourcereader.resource.Resource;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class TransResource implements Resource {
	private File file;
	private String path;

	@Override
	public InputStream getInputStream() throws IOException {
		return new FileInputStream(this.file);
	}

	@Override
	public boolean FileExists() {
		return false;
	}

	public void setFile(File file) {
		this.file = file;
		path = file.getPath();
	}

	public void setPath(String path) {
		this.path = path;
		file = new File(path);
	}

	@Override
	public File getFile() throws IOException {
		return this.file;
	}

	@Override
	public String getDescription() {
		return "资源的描述：" + this.file.getName() + "  地址为：" + this.path;
	}
}
