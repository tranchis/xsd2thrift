namespace * default

struct UnspecifiedType
{
	1 : required string baseObjectType,
	2 : required binary object,
}

struct Choice
{
	1 : optional string a_default_minoccurs_element,
	2 : optional string a_optional_element,
	3 : optional list<string> a_repeat_element,
	4 : optional string a_required_element,
	5 : optional string b_default_minoccurs_element,
	6 : optional string b_optional_element,
	7 : optional list<string> b_repeat_element,
	8 : optional string b_required_element,
}

