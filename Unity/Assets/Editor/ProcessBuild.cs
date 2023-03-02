using System.IO;
using UnityEngine;
using UnityEditor.Callbacks;
using UnityEditor;

#if UNITY_IOS
using UnityEditor.iOS.Xcode;
using UnityEditor.iOS.Xcode.Extensions;
#endif

public class ProcessBuild
{
#if UNITY_IOS
    [PostProcessBuildAttribute(88)]
    public static void onPostProcessBuild(BuildTarget buildTarget, string path)
    {
        if (buildTarget != BuildTarget.iOS)
        {
            Debug.LogWarning("Target is not iPhone. XCodePostProcess will not run");
            return;
        }
        //导入文件
        PBXProject proj = new PBXProject();
        string projPath = path + "/Unity-iPhone.xcodeproj/project.pbxproj";
        proj.ReadFromFile(projPath);
        //xcode Target
        string target = proj.GetUnityMainTargetGuid();
        string unityTarget = proj.GetUnityFrameworkTargetGuid(); 
         
        string fileGuidLibz = proj.AddFile("usr/lib/libz.tbd", "Libraries/libz.tbd", PBXSourceTree.Sdk);
        proj.AddFileToBuild(target, fileGuidLibz);
        proj.AddFileToBuild(unityTarget, fileGuidLibz); 
         
        // rewrite to file
        File.WriteAllText(projPath, proj.WriteToString()); 
    }
#endif
}