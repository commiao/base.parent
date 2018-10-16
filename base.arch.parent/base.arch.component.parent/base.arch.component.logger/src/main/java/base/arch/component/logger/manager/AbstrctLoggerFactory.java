package base.arch.component.logger.manager;

import java.io.IOException;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

import base.arch.component.logger.bean.JarInfo;
import org.apache.commons.io.FilenameUtils;

public abstract class AbstrctLoggerFactory {
    public static JarInfo getJarInfo(Class<?> key) {
        JarInfo jarInfo = new JarInfo();
        JarFile jarFile = null;
        try {
            String path = key.getProtectionDomain().getCodeSource().getLocation().getPath();
            jarFile = new JarFile(path);
            Manifest manifest = jarFile.getManifest();
            if(manifest!=null){
                Attributes mainAttributes = manifest.getMainAttributes();
                if(mainAttributes!=null){
                    jarInfo.setBuildTime(mainAttributes.getValue("Build-Time"));
                    jarInfo.setProjectName(mainAttributes.getValue("Project-name"));
                    jarInfo.setProjectVersion(mainAttributes.getValue("Project-version"));  
                }
            }
            jarInfo.setJarName(FilenameUtils.getName(path));
            jarInfo.setJarPath(path);
        } catch (Exception e) {
        } finally {
            if (jarFile != null) {
                try {
                    jarFile.close();
                } catch (IOException e) {
                }
            }
        }
        return jarInfo;
    }
 
}
