// import java.io.*;
// import java.nio.file.*;
// import java.util.concurrent.TimeUnit;

// public class ApkToolProcessor {

//     // Update these constants for your machine
//     private static final String APK_INPUT_DIR = "F:\\wearable-2-appshark\\wearable-standalone-app";
//     private static final String OUTPUT_ROOT   = "F:\\wearable-2-apktool-app-resource";
//     // apktool.jar must be in the same directory where you run this program, or set an absolute path
//     private static final String APKTOOL_JAR   = "apktool.jar";

//     public static void main(String[] args) {
//         ensureDir(APK_INPUT_DIR);
//         ensureDir(OUTPUT_ROOT);

//         File in = new File(APK_INPUT_DIR);
//         File[] apks = in.listFiles((dir, name) -> name.toLowerCase().endsWith(".apk"));

//         if (apks == null || apks.length == 0) {
//             System.out.println("No APK found in: " + APK_INPUT_DIR);
//             return;
//         }

//         for (File apk : apks) {
//             String apkName = apk.getName(); // e.g., app.groupcal.www.apk
//             String outDir  = Paths.get(OUTPUT_ROOT, apkName).toString();

//             // Recreate output directory (empty) for this APK
//             recreateEmptyDir(outDir);

//             // Use -s to skip sources (no smali). Do NOT use -r so resources are decoded to readable XML.
//             ProcessBuilder pb = new ProcessBuilder(
//                     "java", "-jar", APKTOOL_JAR,
//                     "d", "-f", "-s", 
//                     apk.getAbsolutePath(),
//                     "-o", outDir
//             );

//             System.out.println("-------------------- Decoding resources: " + apkName+" --------------------");
//             boolean ok = runAndPipe(pb, 3600); // timeout: 1 hour per APK

//             // Minimal success check: res/ or res/values/strings.xml exists
//             boolean hasRes = Files.exists(Paths.get(outDir, "res"));
//             boolean hasStrings = Files.exists(Paths.get(outDir, "res", "values", "strings.xml"));

//             if (ok && (hasRes || hasStrings)) {
//                 System.out.println("-------------------- DONE -> " + outDir);
//             } else {
//                 System.out.println("-------------------- FAILED -> " + apkName);
//             }
//             // break;
//         }
//     }

//     // -------- helpers --------
//     private static void ensureDir(String p) {
//         File d = new File(p);
//         if (!d.exists()) d.mkdirs();
//     }

//     private static void recreateEmptyDir(String p) {
//         try {
//             Path path = Paths.get(p);
//             if (Files.exists(path)) {
//                 // delete existing content
//                 Files.walk(path)
//                         .sorted((a, b) -> b.getNameCount() - a.getNameCount())
//                         .forEach(q -> { try { Files.deleteIfExists(q); } catch (IOException ignored) {} });
//             }
//             Files.createDirectories(path);
//         } catch (IOException e) {
//             throw new RuntimeException("Cannot prepare output dir: " + p, e);
//         }
//     }

//     // Run a process, print stdout/stderr to console, return true if exitCode==0 and not timeout
//     private static boolean runAndPipe(ProcessBuilder pb, int timeoutSec) {
//         try {
//             Process p = pb.start();

//             Thread tOut = new Thread(() -> pipe(p.getInputStream(), false));
//             Thread tErr = new Thread(() -> pipe(p.getErrorStream(), true));
//             tOut.start(); tErr.start();

//             boolean finished = p.waitFor(timeoutSec, TimeUnit.SECONDS);
//             if (!finished) {
//                 p.destroyForcibly();
//                 System.out.println("Timeout.");
//                 return false;
//             }
//             int code = p.exitValue();
//             tOut.join(); tErr.join();
//             return code == 0;
//         } catch (Exception e) {
//             System.out.println("Process error: " + e.getMessage());
//             return false;
//         }
//     }

//     private static void pipe(InputStream is, boolean isErr) {
//         try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
//             String line;
//             while ((line = br.readLine()) != null) {
//                 if (isErr) System.out.println("ERR: " + line);
//                 else       System.out.println("OUT: " + line);
//             }
//         } catch (IOException ignored) {}
//     }
// }
import java.io.*;
import java.nio.file.*;
import java.util.concurrent.TimeUnit;

public class ApkToolProcessor {

    private static final String APK_INPUT_DIR = "F:\\wearable-2-appshark\\wearable-app";
    private static final String OUTPUT_ROOT   = "F:\\wearable-2-apktool-app-resource";
    private static final String APKTOOL_JAR   = "apktool.jar";

    public static void main(String[] args) {
        ensureDir(APK_INPUT_DIR);
        ensureDir(OUTPUT_ROOT);

        File in = new File(APK_INPUT_DIR);
        File[] apks = in.listFiles((dir, name) -> name.toLowerCase().endsWith(".apk"));
        if (apks == null || apks.length == 0) {
            System.out.println("No APK found in: " + APK_INPUT_DIR);
            return;
        }

        for (File apk : apks) {
            String apkName = apk.getName();
            String outDir  = Paths.get(OUTPUT_ROOT, apkName).toString();

            recreateEmptyDir(outDir);

            ProcessBuilder pb = new ProcessBuilder(
                    "java", "-jar", APKTOOL_JAR,
                    "d", "-f", "-s",                 // skip smali, keep resources
                    apk.getAbsolutePath(),
                    "-o", outDir
            );

            System.out.println("-------------------- Decoding resources: " + apkName + " --------------------");
            boolean ok = runAndPipe(pb, 3600);

            boolean hasRes = Files.exists(Paths.get(outDir, "res"));
            boolean hasManifest = Files.exists(Paths.get(outDir, "AndroidManifest.xml"));

            if (ok && (hasRes || hasManifest)) {
                // >>> NEW: keep only AndroidManifest.xml and res
                pruneKeepManifestAndRes(outDir);
                System.out.println("-------------------- DONE -> " + outDir);
            } else {
                System.out.println("-------------------- FAILED -> " + apkName);
            }
            // break;
        }
    }

    // --- NEW: delete all except AndroidManifest.xml & res ---
    private static void pruneKeepManifestAndRes(String outDir) {
        File root = new File(outDir);
        File[] items = root.listFiles();
        if (items == null) return;

        for (File f : items) {
            String name = f.getName();
            if (name.equals("AndroidManifest.xml")) continue; // keep file
            if (name.equals("res")) continue;                 // keep directory
            deleteRecursively(f.toPath());                    // remove others
        }
    }

    private static void deleteRecursively(Path p) {
        try {
            if (!Files.exists(p)) return;
            if (Files.isDirectory(p)) {
                // delete children first
                Files.walk(p)
                     .sorted((a, b) -> b.getNameCount() - a.getNameCount())
                     .forEach(q -> { try { Files.deleteIfExists(q); } catch (IOException ignored) {} });
            } else {
                Files.deleteIfExists(p);
            }
        } catch (IOException ignored) {}
    }

    // -------- helpers --------
    private static void ensureDir(String p) {
        File d = new File(p);
        if (!d.exists()) d.mkdirs();
    }

    private static void recreateEmptyDir(String p) {
        try {
            Path path = Paths.get(p);
            if (Files.exists(path)) {
                Files.walk(path)
                        .sorted((a, b) -> b.getNameCount() - a.getNameCount())
                        .forEach(q -> { try { Files.deleteIfExists(q); } catch (IOException ignored) {} });
            }
            Files.createDirectories(path);
        } catch (IOException e) {
            throw new RuntimeException("Cannot prepare output dir: " + p, e);
        }
    }

    private static boolean runAndPipe(ProcessBuilder pb, int timeoutSec) {
        try {
            Process p = pb.start();
            Thread tOut = new Thread(() -> pipe(p.getInputStream(), false));
            Thread tErr = new Thread(() -> pipe(p.getErrorStream(), true));
            tOut.start(); tErr.start();

            boolean finished = p.waitFor(timeoutSec, TimeUnit.SECONDS);
            if (!finished) {
                p.destroyForcibly();
                System.out.println("Timeout.");
                return false;
            }
            int code = p.exitValue();
            tOut.join(); tErr.join();
            return code == 0;
        } catch (Exception e) {
            System.out.println("Process error: " + e.getMessage());
            return false;
        }
    }

    private static void pipe(InputStream is, boolean isErr) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (isErr) System.out.println("ERR: " + line);
                else       System.out.println("OUT: " + line);
            }
        } catch (IOException ignored) {}
    }
}
