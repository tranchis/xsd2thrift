enum EBoolean
{
}

enum EInt
{
}

enum EEnumerator
{
}

enum EString
{
}

enum EJavaClass
{
}

enum EJavaObject
{
}

struct EGenericType
{
	1 : required binary eUpperBound,
	2 : required list<binary> eTypeArguments,
	3 : required binary eLowerBound,
	4 : optional bytes eClassifier,
	5 : optional bytes eRawType,
	6 : optional string eTypeParameter,
}

struct EStringToStringMapEntry
{
	1 : optional string key,
	2 : optional string value,
}

struct EAnnotation
{
	1 : required list<binary> eAnnotations,
	2 : required list<EStringToStringMapEntry> details,
	3 : required list<bytes> contents,
	4 : optional list<bytes> references,
	5 : optional string source,
}

struct EAttribute
{
	1 : required list<EAnnotation> eAnnotations,
	2 : required EGenericType eGenericType,
	3 : optional bytes eAttributeType,
	4 : optional boolean iD,
	5 : optional boolean changeable,
	6 : optional EJavaObject defaultValue,
	7 : optional string defaultValueLiteral,
	8 : optional boolean derived,
	9 : optional boolean _transient,
	10 : optional boolean unsettable,
	11 : optional boolean _volatile,
	12 : optional bytes eType,
	13 : optional i16 lowerBound,
	14 : optional boolean many,
	15 : optional boolean ordered,
	16 : optional boolean _required,
	17 : optional boolean unique,
	18 : optional i16 upperBound,
	19 : optional string name,
}

struct EFactory
{
	1 : required list<EAnnotation> eAnnotations,
	2 : optional string ePackage,
}

struct ETypeParameter
{
	1 : required list<EAnnotation> eAnnotations,
	2 : required list<EGenericType> eBounds,
	3 : optional string name,
}

struct EEnumLiteral
{
	1 : required list<EAnnotation> eAnnotations,
	2 : optional EEnumerator instance,
	3 : optional string literal,
	4 : optional i16 value,
	5 : optional string name,
}

struct EReference
{
	1 : required list<EAnnotation> eAnnotations,
	2 : required EGenericType eGenericType,
	3 : optional boolean container,
	4 : optional boolean containment,
	5 : optional list<bytes> eKeys,
	6 : optional bytes eOpposite,
	7 : optional bytes eReferenceType,
	8 : optional boolean resolveProxies,
	9 : optional boolean changeable,
	10 : optional EJavaObject defaultValue,
	11 : optional string defaultValueLiteral,
	12 : optional boolean derived,
	13 : optional boolean _transient,
	14 : optional boolean unsettable,
	15 : optional boolean _volatile,
	16 : optional bytes eType,
	17 : optional i16 lowerBound,
	18 : optional boolean many,
	19 : optional boolean ordered,
	20 : optional boolean _required,
	21 : optional boolean unique,
	22 : optional i16 upperBound,
	23 : optional string name,
}

struct EStructuralFeature
{
	1 : required list<EAnnotation> eAnnotations,
	2 : required EGenericType eGenericType,
	3 : optional boolean changeable,
	4 : optional EJavaObject defaultValue,
	5 : optional string defaultValueLiteral,
	6 : optional boolean derived,
	7 : optional boolean _transient,
	8 : optional boolean unsettable,
	9 : optional boolean _volatile,
	10 : optional bytes eType,
	11 : optional i16 lowerBound,
	12 : optional boolean many,
	13 : optional boolean ordered,
	14 : optional boolean _required,
	15 : optional boolean unique,
	16 : optional i16 upperBound,
	17 : optional string name,
}

struct EDataType
{
	1 : required list<EAnnotation> eAnnotations,
	2 : required list<ETypeParameter> eTypeParameters,
	3 : optional boolean serializable,
	4 : optional EJavaObject defaultValue,
	5 : optional EJavaClass instanceClass,
	6 : optional string instanceClassName,
	7 : optional string instanceTypeName,
	8 : optional string name,
	9 : optional list<EEnumLiteral> eLiterals,
}

struct EParameter
{
	1 : required list<EAnnotation> eAnnotations,
	2 : required EGenericType eGenericType,
	3 : optional bytes eType,
	4 : optional i16 lowerBound,
	5 : optional boolean many,
	6 : optional boolean ordered,
	7 : optional boolean _required,
	8 : optional boolean unique,
	9 : optional i16 upperBound,
	10 : optional string name,
}

struct EEnum
{
	1 : required list<EAnnotation> eAnnotations,
	2 : required list<ETypeParameter> eTypeParameters,
	3 : required list<EEnumLiteral> eLiterals,
	4 : optional boolean serializable,
	5 : optional EJavaObject defaultValue,
	6 : optional EJavaClass instanceClass,
	7 : optional string instanceClassName,
	8 : optional string instanceTypeName,
	9 : optional string name,
}

struct ETypedElement
{
	1 : required list<EAnnotation> eAnnotations,
	2 : required EGenericType eGenericType,
	3 : optional bytes eType,
	4 : optional i16 lowerBound,
	5 : optional boolean many,
	6 : optional boolean ordered,
	7 : optional boolean _required,
	8 : optional boolean unique,
	9 : optional i16 upperBound,
	10 : optional string name,
	11 : optional list<ETypeParameter> eTypeParameters,
	12 : optional list<EParameter> eParameters,
	13 : optional list<EGenericType> eGenericExceptions,
}

struct EOperation
{
	1 : required list<EAnnotation> eAnnotations,
	2 : required EGenericType eGenericType,
	3 : required list<ETypeParameter> eTypeParameters,
	4 : required list<EParameter> eParameters,
	5 : required list<EGenericType> eGenericExceptions,
	6 : optional list<bytes> eExceptions,
	7 : optional bytes eType,
	8 : optional i16 lowerBound,
	9 : optional boolean many,
	10 : optional boolean ordered,
	11 : optional boolean _required,
	12 : optional boolean unique,
	13 : optional i16 upperBound,
	14 : optional string name,
}

struct EClass
{
	1 : required list<EAnnotation> eAnnotations,
	2 : required list<ETypeParameter> eTypeParameters,
	3 : required list<EOperation> eOperations,
	4 : required list<EStructuralFeature> eStructuralFeatures,
	5 : required list<EGenericType> eGenericSuperTypes,
	6 : optional boolean _abstract,
	7 : optional list<bytes> eAllAttributes,
	8 : optional list<bytes> eAllContainments,
	9 : optional list<bytes> eAllGenericSuperTypes,
	10 : optional list<bytes> eAllOperations,
	11 : optional list<bytes> eAllReferences,
	12 : optional list<bytes> eAllStructuralFeatures,
	13 : optional list<bytes> eAllSuperTypes,
	14 : optional list<bytes> eAttributes,
	15 : optional string eIDAttribute,
	16 : optional list<bytes> eReferences,
	17 : optional list<bytes> eSuperTypes,
	18 : optional boolean _interface,
	19 : optional EJavaObject defaultValue,
	20 : optional EJavaClass instanceClass,
	21 : optional string instanceClassName,
	22 : optional string instanceTypeName,
	23 : optional string name,
}

struct EClassifier
{
	1 : required list<EAnnotation> eAnnotations,
	2 : required list<ETypeParameter> eTypeParameters,
	3 : optional EJavaObject defaultValue,
	4 : optional EJavaClass instanceClass,
	5 : optional string instanceClassName,
	6 : optional string instanceTypeName,
	7 : optional string name,
	8 : optional list<EOperation> eOperations,
	9 : optional list<EStructuralFeature> eStructuralFeatures,
	10 : optional list<EGenericType> eGenericSuperTypes,
	11 : optional list<EEnumLiteral> eLiterals,
}

struct EPackage
{
	1 : required list<EAnnotation> eAnnotations,
	2 : required list<EClassifier> eClassifiers,
	3 : required list<binary> eSubpackages,
	4 : optional string eFactoryInstance,
	5 : optional string nsPrefix,
	6 : optional string nsURI,
	7 : optional string name,
}

struct ENamedElement
{
	1 : required list<EAnnotation> eAnnotations,
	2 : optional string name,
	3 : optional list<ETypeParameter> eTypeParameters,
	4 : optional list<EOperation> eOperations,
	5 : optional list<EStructuralFeature> eStructuralFeatures,
	6 : optional list<EGenericType> eGenericSuperTypes,
	7 : optional list<EEnumLiteral> eLiterals,
	8 : optional list<EClassifier> eClassifiers,
	9 : optional list<EPackage> eSubpackages,
	10 : optional EGenericType eGenericType,
	11 : optional list<EParameter> eParameters,
	12 : optional list<EGenericType> eGenericExceptions,
	13 : optional list<EGenericType> eBounds,
}

struct EModelElement
{
	1 : required list<EAnnotation> eAnnotations,
	2 : optional list<EStringToStringMapEntry> details,
	3 : optional list<bytes> contents,
	4 : optional list<ETypeParameter> eTypeParameters,
	5 : optional list<EOperation> eOperations,
	6 : optional list<EStructuralFeature> eStructuralFeatures,
	7 : optional list<EGenericType> eGenericSuperTypes,
	8 : optional list<EEnumLiteral> eLiterals,
	9 : optional list<EClassifier> eClassifiers,
	10 : optional list<EPackage> eSubpackages,
	11 : optional EGenericType eGenericType,
	12 : optional list<EParameter> eParameters,
	13 : optional list<EGenericType> eGenericExceptions,
	14 : optional list<EGenericType> eBounds,
}

