namespace * schemas.com.domain.common

struct UnspecifiedType
{
	1 : required string baseObjectType,
	2 : required binary object,
}

struct country
{
	1 : required string description,
	2 : required string iso3,
}

