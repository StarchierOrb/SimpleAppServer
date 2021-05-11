package me.starchier.util;

import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.utils.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.util.zip.GZIPInputStream;

public class UnTarUtil {
    private static final Logger LOGGER = LogManager.getLogger(UnTarUtil.class.getName());
    public static void deCompressGZipFile(String tarGzFile, String destDir) {
        TarArchiveEntry entry = null;
        TarArchiveEntry[] subEntries = null;
        File subEntryFile = null;
        try (FileInputStream fis = new FileInputStream(tarGzFile);
             GZIPInputStream gis = new GZIPInputStream(fis);
             TarArchiveInputStream taris = new TarArchiveInputStream(gis);) {
            while ((entry = taris.getNextTarEntry()) != null) {
                StringBuilder entryFileName = new StringBuilder();
                entryFileName.append(destDir).append(File.separator).append(entry.getName());
                File entryFile = new File(entryFileName.toString());
                if (entry.isDirectory()) {
                    if (!entryFile.exists()) {
                        entryFile.mkdir();
                    }
                    subEntries = entry.getDirectoryEntries();
                    for (int i = 0; i < subEntries.length; i++) {
                        try (OutputStream out = new FileOutputStream(subEntryFile)) {
                            String path = entryFileName + File.separator + subEntries[i].getName();
                            subEntryFile = new File(path);
                            org.apache.commons.compress.utils.IOUtils.copy(taris, out);
                        } catch (Exception e) {
                            LOGGER.error("解压缩文件失败:" + subEntries[i].getName() + "in" + tarGzFile);
                        }
                    }
                } else {
                    checkFileExists(entryFile);
                    OutputStream out = new FileOutputStream(entryFile);
                    IOUtils.copy(taris, out);
                    out.close();
                    //如果是gz文件进行递归解压
                    if (entryFile.getName().endsWith(".gz")) {
                        deCompressGZipFile(entryFile.getPath(), destDir);
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.warn("解压缩文件发生错误: ", e);
        }
    }
    public static void checkFileExists(File file) {
        if (file.isDirectory()) {
            if (!file.exists()) {
                file.mkdir();
            }
        } else {
            if (file.getParentFile() != null && !file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            try {
                file.createNewFile();
            } catch (IOException e) {
                LOGGER.warn("检查文件时发生错误: ", e);
            }
        }
    }
}
