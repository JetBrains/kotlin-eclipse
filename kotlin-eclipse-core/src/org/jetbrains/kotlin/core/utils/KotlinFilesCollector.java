package org.jetbrains.kotlin.core.utils;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.jetbrains.kotlin.core.builder.KotlinManager;

public class KotlinFilesCollector {
    
    public static void collectForParsing() {
        try {
            new KotlinFilesCollector().addFilesToParse();
        } catch (CoreException e) {
            e.printStackTrace();
        }
    }
    
    private void addFilesToParse() throws CoreException {
        for (IProject project : ResourcesPlugin.getWorkspace().getRoot().getProjects()) {
            for (IResource resource : project.members(false)) {
                scanForFiles(resource);
            }
        }
    }
    
    private void scanForFiles(IResource parentResource) throws CoreException {
        if (KotlinManager.isCompatibleResource(parentResource)) {
            KotlinManager.updateProjectPsiSources(parentResource, IResourceDelta.ADDED);
            return; 
        }
        if (parentResource.getType() != IResource.FOLDER) {
            return;
        }
        IResource[] resources = ((IFolder) parentResource).members();
        for (IResource resource : resources) {
            scanForFiles(resource);
        }
    }

}
