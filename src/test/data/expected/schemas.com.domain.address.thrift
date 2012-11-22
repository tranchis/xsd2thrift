namespace * schemas.com.domain.address

include "schemas.com.domain.common.thrift"

struct addressLinesType
{
	1 : required list<string> line,
}

struct addressType
{
	1 : required addressLinesType addressLines,
	2 : required string city,
	3 : required string postCode,
	4 : required schemas.com.domain.common.country _country,
}

