package com.liuxk.common.file;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

public class FileIterator {
    private String fileName;

    private long fileLength;

    private long currentPoint;

    private long readRowCount = 0;

    private File file = null;

    private RandomAccessFile randomFile;

    public FileIterator(String fileName) {
        this.fileName = fileName;
    }

    public void init() throws Exception {
        if (StringUtils.isBlank(fileName)) throw new Exception("文件名不能为空");
        file = new File(fileName);
        if (!file.exists() || file.isDirectory() || !file.canRead()) {
            throw new Exception("文件不存在或不可读。");
        }
        randomFile = new RandomAccessFile(file, "r");
        fileLength = randomFile.length();
        currentPoint = randomFile.getFilePointer();
    }

    ;

    public boolean hasNext() {
        return currentPoint < this.fileLength;
    }

    ;

    public byte[] next() throws IOException {
        List<Byte> bytes = new ArrayList<>();
        byte[] tempByte = new byte[1];
        boolean eol = false;
        while (!eol) {
            randomFile.read(tempByte, 0, tempByte.length);
            switch (tempByte[0]) {
                case '\n':
                    eol = true;
                    break;
                case '\r':
                    eol = true;
                    long cur = randomFile.getFilePointer();
                    if ((randomFile.read()) != '\n') {
                        randomFile.seek(cur);
                    }
                    break;
                default:
                    bytes.add(tempByte[0]);
                    break;
            }
            currentPoint = randomFile.getFilePointer();
            readRowCount++;
        }
        return ArrayUtils.toPrimitive(bytes.toArray(new Byte[bytes.size()]));
    }

    ;

    public String getFileName() {
        return fileName;
    }

    public long getFileLength() {
        return fileLength;
    }

    public long getCurrentPoint() {
        return currentPoint;
    }

    public long getReadRowCount() {
        return readRowCount;
    }
}
