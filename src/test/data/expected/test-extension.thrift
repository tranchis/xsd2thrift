namespace * default

struct UnspecifiedType
{
	1 : required string baseObjectType,
	2 : required binary object,
}

struct BaseObject
{
	1 : optional string description,
	2 : required string name,
	3 : optional string value,
}

struct StringObject
{
	1 : optional string description,
	2 : required string name,
	3 : required string value,
}

