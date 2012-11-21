namespace * default

struct UnspecifiedType
{
	1 : required string baseObjectType,
	2 : required binary object,
}

struct OptionalAttributeGroup
{
	1 : optional string no_default_attribute,
	2 : optional string has_default_attribute,
	3 : required string required_attribute,
	4 : optional string optional_attribute,
}

struct Optional
{
	1 : required string default_minoccurs_element,
	2 : optional string optional_element,
	3 : required string required_element,
	4 : optional list<string> repeat_element,
	5 : required string required_attribute,
	6 : optional string has_default_attribute,
	7 : optional string optional_attribute,
	8 : optional string no_default_attribute,
}

