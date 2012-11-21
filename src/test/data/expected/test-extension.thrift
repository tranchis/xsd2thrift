namespace * default

struct UnspecifiedType
{
	1 : required string baseObjectType,
	2 : required binary object,
}

struct StringObject
{
	1 : required string value,
	2 : required string name,
	3 : optional string description,
}

struct BaseObject
{
	1 : required string name,
	2 : optional string description,
	3 : optional string value,
}

