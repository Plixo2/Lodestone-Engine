package net.plixo.paper.client.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;

public class ClassPaths {

    public static ArrayList<String> names = new ArrayList<>();

    static String javaNames = "java.lang.Object\n" +
            "java.io.Serializable\n" +
            "java.lang.Comparable\n" +
            "java.lang.CharSequence\n" +
            "java.lang.String\n" +
            "java.lang.reflect.AnnotatedElement\n" +
            "java.lang.reflect.GenericDeclaration\n" +
            "java.lang.reflect.Type\n" +
            "java.lang.Class\n" +
            "java.lang.Cloneable\n" +
            "java.lang.ClassLoader\n" +
            "java.lang.System\n" +
            "java.lang.Throwable\n" +
            "java.lang.Error\n" +
            "java.lang.ThreadDeath\n" +
            "java.lang.Exception\n" +
            "java.lang.RuntimeException\n" +
            "java.lang.SecurityManager\n" +
            "java.security.ProtectionDomain\n" +
            "java.security.AccessControlContext\n" +
            "java.security.SecureClassLoader\n" +
            "java.lang.ReflectiveOperationException\n" +
            "java.lang.ClassNotFoundException\n" +
            "java.lang.LinkageError\n" +
            "java.lang.NoClassDefFoundError\n" +
            "java.lang.ClassCastException\n" +
            "java.lang.ArrayStoreException\n" +
            "java.lang.VirtualMachineError\n" +
            "java.lang.OutOfMemoryError\n" +
            "java.lang.StackOverflowError\n" +
            "java.lang.IllegalMonitorStateException\n" +
            "java.lang.ref.Reference\n" +
            "java.lang.ref.SoftReference\n" +
            "java.lang.ref.WeakReference\n" +
            "java.lang.ref.FinalReference\n" +
            "java.lang.ref.PhantomReference\n" +
            "java.lang.ref.Finalizer\n" +
            "java.lang.Runnable\n" +
            "java.lang.Thread\n" +
            "java.lang.Thread$UncaughtExceptionHandler\n" +
            "java.lang.ThreadGroup\n" +
            "java.util.Map\n" +
            "java.util.Dictionary\n" +
            "java.util.Hashtable\n" +
            "java.util.Properties\n" +
            "java.lang.Module\n" +
            "java.lang.reflect.AccessibleObject\n" +
            "java.lang.reflect.Member\n" +
            "java.lang.reflect.Field\n" +
            "java.lang.reflect.Parameter\n" +
            "java.lang.reflect.Executable\n" +
            "java.lang.reflect.Method\n" +
            "java.lang.reflect.Constructor\n" +
            "jdk.internal.reflect.MagicAccessorImpl\n" +
            "jdk.internal.reflect.MethodAccessor\n" +
            "jdk.internal.reflect.MethodAccessorImpl\n" +
            "jdk.internal.reflect.ConstructorAccessor\n" +
            "jdk.internal.reflect.ConstructorAccessorImpl\n" +
            "jdk.internal.reflect.DelegatingClassLoader\n" +
            "jdk.internal.reflect.ConstantPool\n" +
            "jdk.internal.reflect.FieldAccessor\n" +
            "jdk.internal.reflect.FieldAccessorImpl\n" +
            "jdk.internal.reflect.UnsafeFieldAccessorImpl\n" +
            "jdk.internal.reflect.UnsafeStaticFieldAccessorImpl\n" +
            "java.lang.annotation.Annotation\n" +
            "jdk.internal.reflect.CallerSensitive\n" +
            "java.lang.invoke.MethodHandle\n" +
            "java.lang.invoke.DirectMethodHandle\n" +
            "java.lang.invoke.VarHandle\n" +
            "java.lang.invoke.MemberName\n" +
            "java.lang.invoke.ResolvedMethodName\n" +
            "java.lang.invoke.MethodHandleNatives\n" +
            "java.lang.invoke.LambdaForm\n" +
            "java.lang.invoke.MethodType\n" +
            "java.lang.BootstrapMethodError\n" +
            "java.lang.invoke.CallSite\n" +
            "java.lang.invoke.MethodHandleNatives$CallSiteContext\n" +
            "java.lang.invoke.ConstantCallSite\n" +
            "java.lang.invoke.MutableCallSite\n" +
            "java.lang.invoke.VolatileCallSite\n" +
            "java.lang.AssertionStatusDirectives\n" +
            "java.lang.Appendable\n" +
            "java.lang.AbstractStringBuilder\n" +
            "java.lang.StringBuffer\n" +
            "java.lang.StringBuilder\n" +
            "jdk.internal.misc.Unsafe\n" +
            "jdk.internal.module.Modules\n" +
            "java.lang.AutoCloseable\n" +
            "java.io.Closeable\n" +
            "java.io.InputStream\n" +
            "java.io.ByteArrayInputStream\n" +
            "java.net.URL\n" +
            "java.util.jar.Manifest\n" +
            "jdk.internal.loader.ClassLoaders\n" +
            "jdk.internal.loader.BuiltinClassLoader\n" +
            "jdk.internal.loader.ClassLoaders$AppClassLoader\n" +
            "jdk.internal.loader.ClassLoaders$PlatformClassLoader\n" +
            "java.security.CodeSource\n" +
            "java.lang.StackTraceElement\n" +
            "java.nio.Buffer\n" +
            "java.lang.StackWalker\n" +
            "java.lang.StackStreamFactory$AbstractStackWalker\n" +
            "java.lang.StackWalker$StackFrame\n" +
            "java.lang.StackFrameInfo\n" +
            "java.lang.LiveStackFrame\n" +
            "java.lang.LiveStackFrameInfo\n" +
            "java.util.concurrent.locks.AbstractOwnableSynchronizer\n" +
            "java.lang.Boolean\n" +
            "java.lang.Character\n" +
            "java.lang.Number\n" +
            "java.lang.Float\n" +
            "java.lang.Double\n" +
            "java.lang.Byte\n" +
            "java.lang.Short\n" +
            "java.lang.Integer\n" +
            "java.lang.Long\n" +
            "java.lang.NullPointerException\n" +
            "java.lang.ArithmeticException\n" +
            "java.io.ObjectStreamField\n" +
            "java.util.Comparator\n" +
            "java.lang.String$CaseInsensitiveComparator\n" +
            "java.security.AccessController\n" +
            "java.lang.Iterable\n" +
            "java.util.Collection\n" +
            "java.util.Set\n" +
            "java.util.AbstractCollection\n" +
            "java.util.ImmutableCollections$AbstractImmutableCollection\n" +
            "java.util.ImmutableCollections$AbstractImmutableSet\n" +
            "java.util.ImmutableCollections$Set12\n" +
            "java.util.Objects\n" +
            "java.security.PrivilegedAction\n" +
            "jdk.internal.reflect.ReflectionFactory$GetReflectionFactoryAction\n" +
            "java.security.cert.Certificate\n" +
            "java.util.AbstractSet\n" +
            "java.util.HashSet\n" +
            "java.util.AbstractMap\n" +
            "java.util.HashMap\n" +
            "java.util.concurrent.ConcurrentMap\n" +
            "java.util.concurrent.ConcurrentHashMap\n" +
            "java.lang.Runtime\n" +
            "java.util.concurrent.locks.Lock\n" +
            "java.util.concurrent.locks.ReentrantLock\n" +
            "java.util.concurrent.ConcurrentHashMap$Segment\n" +
            "java.util.concurrent.ConcurrentHashMap$CounterCell\n" +
            "java.util.Map$Entry\n" +
            "java.util.concurrent.ConcurrentHashMap$Node\n" +
            "java.util.concurrent.locks.LockSupport\n" +
            "java.util.concurrent.ConcurrentHashMap$ReservationNode\n" +
            "jdk.internal.reflect.ReflectionFactory\n" +
            "java.lang.ref.Reference$ReferenceHandler\n" +
            "jdk.internal.ref.Cleaner\n" +
            "java.lang.ref.ReferenceQueue\n" +
            "java.lang.ref.ReferenceQueue$Null\n" +
            "java.lang.ref.ReferenceQueue$Lock\n" +
            "jdk.internal.misc.JavaLangRefAccess\n" +
            "java.lang.ref.Reference$1\n" +
            "jdk.internal.misc.SharedSecrets\n" +
            "java.lang.ref.Finalizer$FinalizerThread\n" +
            "jdk.internal.misc.VM\n" +
            "java.lang.StringLatin1\n" +
            "java.util.Properties$EntrySet\n" +
            "java.util.concurrent.ConcurrentHashMap$CollectionView\n" +
            "java.util.concurrent.ConcurrentHashMap$EntrySetView\n" +
            "java.util.Collections\n" +
            "java.util.Collections$EmptySet\n" +
            "java.util.RandomAccess\n" +
            "java.util.List\n" +
            "java.util.AbstractList\n" +
            "java.util.Collections$EmptyList\n" +
            "java.util.Collections$EmptyMap\n" +
            "java.util.Collections$SynchronizedCollection\n" +
            "java.util.Collections$SynchronizedSet\n" +
            "java.lang.reflect.Array\n" +
            "java.util.Iterator\n" +
            "java.util.concurrent.ConcurrentHashMap$Traverser\n" +
            "java.util.concurrent.ConcurrentHashMap$BaseIterator\n" +
            "java.util.concurrent.ConcurrentHashMap$EntryIterator\n" +
            "java.util.concurrent.ConcurrentHashMap$MapEntry\n" +
            "java.util.ImmutableCollections$AbstractImmutableMap\n" +
            "java.util.ImmutableCollections$MapN\n" +
            "java.lang.Math\n" +
            "jdk.internal.util.StaticProperty\n" +
            "java.lang.VersionProps\n" +
            "java.io.FileInputStream\n" +
            "java.io.FileDescriptor\n" +
            "jdk.internal.misc.JavaIOFileDescriptorAccess\n" +
            "java.io.FileDescriptor$1\n" +
            "java.io.Flushable\n" +
            "java.io.OutputStream\n" +
            "java.io.FileOutputStream\n" +
            "java.io.FilterInputStream\n" +
            "java.io.BufferedInputStream\n" +
            "java.io.FilterOutputStream\n" +
            "java.io.PrintStream\n" +
            "java.io.BufferedOutputStream\n" +
            "java.nio.charset.Charset\n" +
            "java.nio.charset.spi.CharsetProvider\n" +
            "sun.nio.cs.StandardCharsets\n" +
            "java.lang.ThreadLocal\n" +
            "java.util.concurrent.atomic.AtomicInteger\n" +
            "sun.util.PreHashedMap\n" +
            "sun.nio.cs.StandardCharsets$Aliases\n" +
            "sun.nio.cs.StandardCharsets$Cache\n" +
            "sun.nio.cs.HistoricallyNamedCharset\n" +
            "sun.nio.cs.Unicode\n" +
            "sun.nio.cs.UTF_8\n" +
            "sun.nio.cs.ISO_8859_1\n" +
            "sun.nio.cs.US_ASCII\n" +
            "java.nio.charset.StandardCharsets\n" +
            "sun.nio.cs.UTF_16BE\n" +
            "sun.nio.cs.UTF_16LE\n" +
            "sun.nio.cs.UTF_16\n" +
            "sun.nio.cs.StandardCharsets$Classes\n" +
            "java.util.Arrays\n" +
            "sun.nio.cs.IBM850\n" +
            "java.lang.StringUTF16\n" +
            "sun.nio.cs.SingleByte\n" +
            "java.lang.Class$ReflectionData\n" +
            "java.lang.Class$Atomic\n" +
            "java.lang.reflect.Modifier\n" +
            "jdk.internal.reflect.LangReflectAccess\n" +
            "java.lang.reflect.ReflectAccess\n" +
            "java.lang.Class$1\n" +
            "jdk.internal.reflect.Reflection\n" +
            "java.util.HashMap$Node\n" +
            "jdk.internal.reflect.NativeConstructorAccessorImpl\n" +
            "jdk.internal.reflect.DelegatingConstructorAccessorImpl\n" +
            "java.io.Writer\n" +
            "java.io.OutputStreamWriter\n" +
            "sun.nio.cs.StreamEncoder\n" +
            "sun.nio.cs.ArrayEncoder\n" +
            "java.nio.charset.CharsetEncoder\n" +
            "sun.nio.cs.SingleByte$Encoder\n" +
            "java.nio.charset.CodingErrorAction\n" +
            "java.nio.ByteBuffer\n" +
            "jdk.internal.misc.JavaNioAccess\n" +
            "java.nio.Buffer$1\n" +
            "java.nio.HeapByteBuffer\n" +
            "java.nio.ByteOrder\n" +
            "java.io.BufferedWriter\n" +
            "java.lang.Terminator\n" +
            "jdk.internal.misc.Signal$Handler\n" +
            "java.lang.Terminator$1\n" +
            "jdk.internal.misc.Signal\n" +
            "java.util.Hashtable$Entry\n" +
            "jdk.internal.misc.Signal$NativeHandler\n" +
            "java.lang.Integer$IntegerCache\n" +
            "jdk.internal.misc.OSEnvironment\n" +
            "sun.io.Win32ErrorMode\n" +
            "jdk.internal.misc.JavaLangAccess\n" +
            "java.lang.System$2\n" +
            "java.io.File\n" +
            "java.io.DefaultFileSystem\n" +
            "java.io.FileSystem\n" +
            "java.io.WinNTFileSystem\n" +
            "java.io.ExpiringCache\n" +
            "java.util.LinkedHashMap\n" +
            "java.io.ExpiringCache$1\n" +
            "sun.security.action.GetPropertyAction\n" +
            "java.lang.IllegalArgumentException\n" +
            "java.lang.invoke.MethodHandleStatics\n" +
            "java.lang.CharacterData\n" +
            "java.lang.CharacterDataLatin1\n" +
            "jdk.internal.module.ModuleBootstrap\n" +
            "java.lang.module.ModuleDescriptor\n" +
            "jdk.internal.misc.JavaLangModuleAccess\n" +
            "java.lang.module.ModuleDescriptor$1\n" +
            "java.util.ImmutableCollections\n" +
            "jdk.internal.module.ModulePatcher\n" +
            "java.util.ImmutableCollections$SetN\n" +
            "jdk.internal.module.ArchivedModuleGraph\n" +
            "jdk.internal.module.SystemModuleFinders\n" +
            "java.net.URI\n" +
            "jdk.internal.misc.JavaNetUriAccess\n" +
            "java.net.URI$1\n" +
            "jdk.internal.module.SystemModulesMap\n" +
            "jdk.internal.module.SystemModules\n" +
            "jdk.internal.module.SystemModules$default\n" +
            "jdk.internal.module.Builder\n" +
            "java.lang.module.ModuleDescriptor$Requires\n" +
            "java.lang.module.ModuleDescriptor$Exports\n" +
            "java.lang.module.ModuleDescriptor$Opens\n" +
            "java.lang.module.ModuleDescriptor$Provides\n" +
            "java.util.ImmutableCollections$AbstractImmutableList\n" +
            "java.util.ImmutableCollections$List12\n" +
            "java.lang.module.ModuleDescriptor$Version\n" +
            "java.util.ArrayList\n" +
            "java.lang.Enum\n" +
            "java.lang.module.ModuleDescriptor$Modifier\n" +
            "java.lang.module.ModuleDescriptor$Requires$Modifier\n" +
            "java.util.ImmutableCollections$ListN\n" +
            "jdk.internal.module.ModuleTarget\n" +
            "jdk.internal.module.ModuleHashes\n" +
            "jdk.internal.module.ModuleHashes$Builder\n" +
            "java.util.LinkedHashMap$Entry\n" +
            "java.util.HashMap$TreeNode\n" +
            "java.util.Collections$UnmodifiableMap\n" +
            "jdk.internal.module.ModuleResolution\n" +
            "java.lang.module.ModuleReference\n" +
            "java.util.function.Supplier\n" +
            "jdk.internal.module.SystemModuleFinders$2\n" +
            "jdk.internal.module.ModuleReferenceImpl\n" +
            "java.util.KeyValueHolder\n" +
            "jdk.internal.module.ModuleHashes$HashSupplier\n" +
            "jdk.internal.module.SystemModuleFinders$3\n" +
            "java.lang.module.ModuleFinder\n" +
            "jdk.internal.module.SystemModuleFinders$SystemModuleFinder\n" +
            "jdk.internal.module.ModuleBootstrap$Counters\n" +
            "java.util.Optional\n" +
            "jdk.internal.loader.BootLoader\n" +
            "jdk.internal.module.ServicesCatalog\n" +
            "jdk.internal.loader.AbstractClassLoaderValue\n" +
            "jdk.internal.loader.ClassLoaderValue\n" +
            "jdk.internal.loader.ClassLoaders$BootClassLoader\n" +
            "java.lang.ClassLoader$ParallelLoaders\n" +
            "java.util.WeakHashMap\n" +
            "java.util.WeakHashMap$Entry\n" +
            "java.util.Collections$SetFromMap\n" +
            "java.util.WeakHashMap$KeySet\n" +
            "java.util.Vector\n" +
            "jdk.internal.misc.JavaSecurityAccess\n" +
            "java.security.ProtectionDomain$JavaSecurityAccessImpl\n" +
            "java.security.ProtectionDomain$Key\n" +
            "java.security.Principal\n" +
            "jdk.internal.loader.URLClassPath\n" +
            "java.net.URLStreamHandlerFactory\n" +
            "java.net.URL$DefaultFactory\n" +
            "jdk.internal.misc.JavaNetURLAccess\n" +
            "java.net.URL$3\n" +
            "java.io.File$PathStatus\n" +
            "java.io.ExpiringCache$Entry\n" +
            "sun.net.www.ParseUtil\n" +
            "java.net.URLStreamHandler\n" +
            "sun.net.www.protocol.file.Handler\n" +
            "sun.net.util.IPAddressUtil\n" +
            "java.util.Queue\n" +
            "java.util.Deque\n" +
            "java.util.ArrayDeque\n" +
            "jdk.internal.util.Preconditions\n" +
            "jdk.internal.loader.BuiltinClassLoader$LoadedModule\n" +
            "sun.net.www.protocol.jrt.Handler\n" +
            "java.util.ImmutableCollections$SetN$SetNIterator\n" +
            "java.lang.module.Configuration\n" +
            "java.lang.module.ResolvedModule\n" +
            "java.util.AbstractMap$1\n" +
            "java.util.AbstractMap$1$1\n" +
            "java.util.ImmutableCollections$MapN$1\n" +
            "java.util.ImmutableCollections$MapN$MapNIterator\n" +
            "java.util.ImmutableCollections$Set12$1\n" +
            "jdk.internal.module.ModuleLoaderMap\n" +
            "java.util.function.Function\n" +
            "jdk.internal.module.ModuleLoaderMap$Mapper\n" +
            "java.util.concurrent.ConcurrentHashMap$ForwardingNode\n" +
            "java.lang.ModuleLayer\n" +
            "java.util.ListIterator\n" +
            "java.util.ImmutableCollections$ListItr\n" +
            "java.util.ArrayList$Itr\n" +
            "java.util.Collections$UnmodifiableCollection\n" +
            "java.util.Collections$UnmodifiableSet\n" +
            "java.util.Collections$UnmodifiableCollection$1\n" +
            "java.util.concurrent.CopyOnWriteArrayList\n" +
            "jdk.internal.module.ServicesCatalog$ServiceProvider\n" +
            "java.util.HashMap$KeySet\n" +
            "java.util.HashMap$HashIterator\n" +
            "java.util.HashMap$KeyIterator\n" +
            "java.lang.ModuleLayer$Controller\n" +
            "jdk.internal.module.IllegalAccessLogger$Mode\n" +
            "jdk.internal.module.IllegalAccessLogger$Builder\n" +
            "java.util.HashMap$Values\n" +
            "java.util.HashMap$ValueIterator\n" +
            "jdk.internal.module.ModuleBootstrap$2\n" +
            "java.util.HashMap$EntrySet\n" +
            "java.util.HashMap$EntryIterator\n" +
            "jdk.internal.module.IllegalAccessLogger\n" +
            "sun.launcher.LauncherHelper\n" +
            "sun.launcher.LauncherHelper$ResourceBundleHolder\n" +
            "java.util.ResourceBundle\n" +
            "jdk.internal.misc.JavaUtilResourceBundleAccess\n" +
            "java.util.ResourceBundle$1\n" +
            "java.util.ResourceBundle$2\n" +
            "java.util.Locale\n" +
            "sun.util.locale.LocaleObjectCache\n" +
            "java.util.Locale$Cache\n" +
            "sun.util.locale.BaseLocale\n" +
            "sun.util.locale.BaseLocale$Cache\n" +
            "sun.util.locale.BaseLocale$Key\n" +
            "sun.util.locale.LocaleObjectCache$CacheEntry\n" +
            "sun.util.locale.LocaleUtils\n" +
            "sun.util.locale.InternalLocaleBuilder\n" +
            "sun.util.locale.InternalLocaleBuilder$CaseInsensitiveChar\n" +
            "java.util.ResourceBundle$Control\n" +
            "java.util.ResourceBundle$Control$CandidateListCache\n" +
            "java.lang.invoke.LambdaMetafactory\n" +
            "java.lang.invoke.MethodHandles$Lookup\n" +
            "java.lang.invoke.MethodType$ConcurrentWeakInternSet\n" +
            "java.lang.invoke.MethodType$ConcurrentWeakInternSet$WeakEntry\n" +
            "java.lang.Void\n" +
            "java.lang.invoke.MethodTypeForm\n" +
            "java.lang.invoke.MethodHandles\n" +
            "java.lang.invoke.MemberName$Factory\n" +
            "java.security.Guard\n" +
            "java.security.Permission\n" +
            "java.security.BasicPermission\n" +
            "java.lang.reflect.ReflectPermission\n" +
            "sun.invoke.util.VerifyAccess\n" +
            "sun.invoke.util.Wrapper\n" +
            "sun.invoke.util.Wrapper$Format\n" +
            "java.lang.invoke.LambdaForm$NamedFunction\n" +
            "java.lang.invoke.DirectMethodHandle$Holder\n" +
            "sun.invoke.util.ValueConversions\n" +
            "java.lang.invoke.MethodHandleImpl\n" +
            "java.lang.invoke.Invokers\n" +
            "java.lang.invoke.LambdaForm$Kind\n" +
            "java.lang.NoSuchMethodException\n" +
            "java.lang.invoke.LambdaForm$BasicType\n" +
            "java.lang.invoke.LambdaForm$Name\n" +
            "java.lang.invoke.LambdaForm$Holder\n" +
            "java.lang.invoke.MethodHandleImpl$Intrinsic\n" +
            "java.lang.invoke.InvokerBytecodeGenerator\n" +
            "java.lang.invoke.InvokerBytecodeGenerator$2\n" +
            "java.lang.invoke.BootstrapMethodInvoker\n" +
            "java.lang.invoke.Invokers$Holder\n" +
            "jdk.internal.misc.JavaLangInvokeAccess\n" +
            "java.lang.invoke.MethodHandleImpl$1\n" +
            "java.lang.invoke.AbstractValidatingLambdaMetafactory\n" +
            "java.lang.invoke.InnerClassLambdaMetafactory\n" +
            "jdk.internal.org.objectweb.asm.Type\n" +
            "sun.security.action.GetBooleanAction\n" +
            "java.lang.invoke.MethodHandleInfo\n" +
            "java.lang.invoke.InfoFromMemberName\n" +
            "sun.invoke.util.BytecodeDescriptor\n" +
            "jdk.internal.org.objectweb.asm.ClassVisitor\n" +
            "jdk.internal.org.objectweb.asm.ClassWriter\n" +
            "jdk.internal.org.objectweb.asm.ByteVector\n" +
            "jdk.internal.org.objectweb.asm.Item\n" +
            "jdk.internal.org.objectweb.asm.FieldVisitor\n" +
            "jdk.internal.org.objectweb.asm.FieldWriter\n" +
            "jdk.internal.org.objectweb.asm.MethodVisitor\n" +
            "jdk.internal.org.objectweb.asm.MethodWriter\n" +
            "jdk.internal.org.objectweb.asm.Label\n" +
            "jdk.internal.org.objectweb.asm.Frame\n" +
            "jdk.internal.org.objectweb.asm.AnnotationVisitor\n" +
            "jdk.internal.org.objectweb.asm.AnnotationWriter\n" +
            "java.lang.invoke.TypeConvertingMethodAdapter\n" +
            "java.lang.invoke.InnerClassLambdaMetafactory$ForwardingMethodGenerator\n" +
            "java.util.ResourceBundle$$Lambda$1/0x0000000100060040\n" +
            "jdk.internal.ref.CleanerFactory\n" +
            "java.util.concurrent.ThreadFactory\n" +
            "jdk.internal.ref.CleanerFactory$1\n" +
            "java.lang.ref.Cleaner\n" +
            "java.lang.ref.Cleaner$1\n" +
            "jdk.internal.ref.CleanerImpl\n" +
            "java.lang.ref.Cleaner$Cleanable\n" +
            "jdk.internal.ref.PhantomCleanable\n" +
            "jdk.internal.ref.CleanerImpl$PhantomCleanableRef\n" +
            "jdk.internal.ref.WeakCleanable\n" +
            "jdk.internal.ref.CleanerImpl$WeakCleanableRef\n" +
            "jdk.internal.ref.SoftCleanable\n" +
            "jdk.internal.ref.CleanerImpl$SoftCleanableRef\n" +
            "jdk.internal.ref.CleanerImpl$CleanerCleanable\n" +
            "jdk.internal.ref.CleanerFactory$1$1\n" +
            "jdk.internal.misc.InnocuousThread\n" +
            "jdk.internal.misc.InnocuousThread$3\n" +
            "jdk.internal.misc.InnocuousThread$2\n" +
            "java.util.ResourceBundle$CacheKey\n" +
            "java.util.ResourceBundle$CacheKeyReference\n" +
            "java.util.ResourceBundle$KeyElementReference\n" +
            "java.util.AbstractSequentialList\n" +
            "java.util.LinkedList\n" +
            "java.util.LinkedList$Node\n" +
            "java.util.ResourceBundle$3\n" +
            "java.util.ResourceBundle$ResourceBundleProviderHelper\n" +
            "java.util.ResourceBundle$ResourceBundleProviderHelper$$Lambda$2/0x0000000100060440\n" +
            "sun.security.util.SecurityConstants\n" +
            "java.security.AllPermission\n" +
            "java.net.NetPermission\n" +
            "java.lang.RuntimePermission\n" +
            "java.security.SecurityPermission\n" +
            "java.net.SocketPermission\n" +
            "java.security.AccessController$1\n" +
            "java.security.PermissionCollection\n" +
            "java.security.Permissions\n" +
            "java.security.Permissions$1\n" +
            "java.security.AllPermissionCollection\n" +
            "java.security.UnresolvedPermission\n" +
            "sun.security.util.FilePermCompat\n" +
            "sun.security.util.SecurityProperties\n" +
            "java.security.Security\n" +
            "sun.security.util.Debug\n" +
            "java.security.Security$1\n" +
            "java.io.FileCleanable\n" +
            "java.util.Properties$LineReader\n" +
            "java.io.FileInputStream$1\n" +
            "java.io.FilePermission\n" +
            "java.util.ListResourceBundle\n" +
            "sun.launcher.resources.launcher\n" +
            "java.util.ResourceBundle$ResourceBundleProviderHelper$$Lambda$3/0x0000000100060840\n" +
            "java.util.ResourceBundle$BundleReference\n" +
            "sun.launcher.resources.launcher_de\n" +
            "java.util.ResourceBundle$ResourceBundleProviderHelper$$Lambda$4/0x0000000100060c40\n" +
            "jdk.internal.module.Resources\n" +
            "jdk.internal.module.Checks\n" +
            "jdk.internal.loader.BuiltinClassLoader$5\n" +
            "java.lang.module.ModuleReader\n" +
            "jdk.internal.module.SystemModuleFinders$SystemModuleReader\n" +
            "jdk.internal.module.SystemModuleFinders$SystemImage\n" +
            "jdk.internal.jimage.ImageReaderFactory\n" +
            "java.nio.file.Paths\n" +
            "java.nio.file.Watchable\n" +
            "java.nio.file.Path\n" +
            "java.nio.file.FileSystems\n" +
            "java.nio.file.FileSystems$DefaultFileSystemHolder\n" +
            "java.nio.file.FileSystems$DefaultFileSystemHolder$1\n" +
            "sun.nio.fs.DefaultFileSystemProvider\n" +
            "java.nio.file.spi.FileSystemProvider\n" +
            "sun.nio.fs.AbstractFileSystemProvider\n" +
            "sun.nio.fs.WindowsFileSystemProvider\n" +
            "java.nio.file.OpenOption\n" +
            "java.nio.file.StandardOpenOption\n" +
            "java.nio.file.FileSystem\n" +
            "sun.nio.fs.WindowsFileSystem\n" +
            "java.util.Arrays$ArrayList\n" +
            "java.util.Arrays$ArrayItr\n" +
            "sun.nio.fs.WindowsPathParser\n" +
            "sun.nio.fs.WindowsPathType\n" +
            "sun.nio.fs.WindowsPathParser$Result\n" +
            "java.net.URI$Parser\n" +
            "sun.nio.fs.WindowsPath\n" +
            "jdk.internal.jimage.ImageReaderFactory$1\n" +
            "jdk.internal.jimage.ImageReader\n" +
            "jdk.internal.jimage.BasicImageReader\n" +
            "jdk.internal.jimage.ImageReader$SharedImageReader\n" +
            "jdk.internal.jimage.BasicImageReader$1\n" +
            "jdk.internal.jimage.NativeImageBuffer\n" +
            "jdk.internal.jimage.NativeImageBuffer$1\n" +
            "java.lang.ClassLoader$2\n" +
            "java.lang.ClassLoader$NativeLibrary\n" +
            "java.util.ArrayDeque$DeqIterator\n" +
            "java.util.concurrent.ConcurrentHashMap$ValuesView\n" +
            "java.util.Enumeration\n" +
            "java.util.concurrent.ConcurrentHashMap$ValueIterator\n" +
            "sun.nio.ch.DirectBuffer\n" +
            "java.nio.MappedByteBuffer\n" +
            "java.nio.DirectByteBuffer\n" +
            "java.nio.Bits\n" +
            "java.util.concurrent.atomic.AtomicLong\n" +
            "jdk.internal.misc.JavaNioAccess$BufferPool\n" +
            "java.nio.Bits$1\n" +
            "jdk.internal.jimage.ImageHeader\n" +
            "java.nio.IntBuffer\n" +
            "java.nio.DirectIntBufferU\n" +
            "java.nio.DirectByteBufferR\n" +
            "java.nio.DirectIntBufferRU\n" +
            "jdk.internal.jimage.ImageStrings\n" +
            "jdk.internal.jimage.ImageStringsReader\n" +
            "jdk.internal.jimage.decompressor.Decompressor\n" +
            "java.lang.invoke.DirectMethodHandle$Special\n" +
            "jdk.internal.module.SystemModuleFinders$SystemModuleReader$$Lambda$5/0x0000000100067840\n" +
            "java.text.Format\n" +
            "java.text.MessageFormat\n" +
            "java.util.Locale$Category\n" +
            "java.util.Locale$1\n" +
            "java.text.FieldPosition\n" +
            "java.util.Date\n" +
            "java.text.AttributedCharacterIterator$Attribute\n" +
            "java.text.Format$Field\n" +
            "java.text.MessageFormat$Field\n" +
            "java.lang.Readable\n" +
            "java.nio.CharBuffer\n" +
            "java.nio.HeapCharBuffer\n" +
            "java.nio.charset.CoderResult\n" +
            "java.lang.Shutdown\n" +
            "java.lang.Shutdown$Lock\n";

    public static void generate() {
        names.clear();
        try {
            ClassLoader myCL = Thread.currentThread().getContextClassLoader();
            while (myCL != null) {
                for (Iterator iter = list(myCL); iter.hasNext(); ) {
                    Class clazz = (Class) iter.next();
                    String txt = clazz.getName();
                  //  Util.print(txt);
                    if (txt.contains("$")) {
                        continue;
                    }

                    names.add(txt);
                }
                myCL = myCL.getParent();
            }
        }
        catch (Exception e) {
            Util.print(e);
            e.printStackTrace();
        }
        int dyn = names.size();


        for (String s : javaNames.split("\n")) {
            if (s.contains("$")) {
                continue;
            }
            names.add(s);
        }

        Util.print("Loaded " + dyn + " classes with extra " + (names.size()-dyn) + " standard classes");
    }

    static Iterator list(ClassLoader CL)
            throws NoSuchFieldException, SecurityException,
            IllegalArgumentException, IllegalAccessException {
        Class CL_class = CL.getClass();
        while (CL_class != java.lang.ClassLoader.class) {
            CL_class = CL_class.getSuperclass();
        }
        java.lang.reflect.Field ClassLoader_classes_field = CL_class
                .getDeclaredField("classes");
        ClassLoader_classes_field.setAccessible(true);
        Vector classes = (Vector) ClassLoader_classes_field.get(CL);
        return classes.iterator();
    }


}
