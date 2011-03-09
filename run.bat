@REM
@REM Copyright (c) 2010, Institute of Telematics, University of Luebeck
@REM All rights reserved.
@REM
@REM Redistribution and use in source and binary forms, with or without modification, are permitted provided that the
@REM following conditions are met:
@REM
@REM 	- Redistributions of source code must retain the above copyright notice, this list of conditions and the following
@REM 	  disclaimer.
@REM 	- Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the
@REM 	  following disclaimer in the documentation and/or other materials provided with the distribution.
@REM 	- Neither the name of the University of Luebeck nor the names of its contributors may be used to endorse or promote
@REM 	  products derived from this software without specific prior written permission.
@REM
@REM THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES,
@REM INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
@REM ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT,
@REM INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE
@REM GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
@REM LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY
@REM OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
@REM

mvn exec:java -Dexec.mainClass=com.github.tranchis.xsd2thrift.Main -Dexec.args="%*"