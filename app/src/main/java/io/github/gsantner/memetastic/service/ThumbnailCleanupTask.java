package io.github.gsantner.memetastic.service;

import android.content.Context;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import io.github.gsantner.memetastic.R;
import io.github.gsantner.memetastic.util.ContextUtils;

public class ThumbnailCleanupTask extends Thread implements FilenameFilter {
    private String strres_dotThumbnails;

    public ThumbnailCleanupTask(Context context) {
        strres_dotThumbnails = context.getString(R.string.pref_key__language);
    }

    public void run() {
        ContextUtils cu = ContextUtils.get();

        /*File picPath = cu.getPicturesMemetasticFolder();
        File thumbPath = new File(picPath, strres_dotThumbnails);
        cleanupThumbnails(picPath, thumbPath);


        picPath = cu.getPicturesMemetasticTemplatesCustomFolder();
        thumbPath = new File(picPath, strres_dotThumbnails);
        cleanupThumbnails(picPath, thumbPath);*/
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private void cleanupThumbnails(File picPath, File thumbPath) {

        // Scan for unused Thumbnails
        File[] tmp = thumbPath.listFiles(this);
        tmp = tmp != null ? tmp : new File[0];
        List<File> thumbFiles = new LinkedList<File>(Arrays.asList(tmp));
        tmp = picPath.listFiles(this);
        for (File picFile : (tmp == null ? new File[0] : tmp)) {
            String filename = picFile.getName();
            for (int i = 0; i < thumbFiles.size(); i++) {
                if (thumbFiles.get(i).getName().equals(filename)) {
                    thumbFiles.remove(i);
                    break;
                }
            }
        }

        // Delete unused thumbnails
        for (File thumbFile : thumbFiles) {
            thumbFile.delete();
        }

        // Create not existing thumbs
        /*MemeOriginStorage memeOriginStorage = new MemeOriginStorage(picPath, strres_dotThumbnails);
        Map<String, String> missing = memeOriginStorage.getMissingThumbnails();
        for (Map.Entry<String, String> entry : missing.entrySet()) {
            Bitmap bitmap = ContextUtils.get().loadImageFromFilesystem(entry.getKey());
            if (bitmap != null) {
                File thumbFp = new File(entry.getValue());
                bitmap = ContextUtils.get().scaleBitmap(bitmap);
                ContextUtils.get().writeImageToFileJpeg(thumbFp.getParent(), thumbFp.getName(), bitmap);
            }
        }*/
    }

    @Override
    public boolean accept(File dir, String filename) {
        return new File(dir, filename).isFile();
    }
}