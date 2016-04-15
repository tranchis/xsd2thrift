namespace * default

struct UnspecifiedType
{
	1 : required string baseObjectType,
	2 : required binary object,
}

struct Optional
{
	1 : required string default_minoccurs_element,
	2 : optional string has_default_attribute,
	3 : optional string no_default_attribute,
	4 : optional string optional_attribute,
	5 : optional string optional_element,
	6 : optional list<string> repeat_element,
	7 : required string required_attribute,
	8 : required string required_element,
}

struct OptionalAttributeGroup
{
	1 : optional string has_default_attribute,
	2 : optional string no_default_attribute,
	3 : optional string optional_attribute,
	4 : required string required_attribute,
}

