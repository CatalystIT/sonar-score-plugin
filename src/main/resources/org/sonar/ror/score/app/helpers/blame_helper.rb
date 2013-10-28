#	Copyright 2013 Catalyst IT Services
#
#	Licensed under the Apache License, Version 2.0 (the "License");
#	you may not use this file except in compliance with the License.
#	You may obtain a copy of the License at
#
#		http://www.apache.org/licenses/LICENSE-2.0
#
#   Unless required by applicable law or agreed to in writing, software
#   distributed under the License is distributed on an "AS IS" BASIS,
#   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
#   See the License for the specific language governing permissions and
#   limitations under the License.

module BlameHelper

	def getMetric
	  metric = "the metric is 5"
	  return metric
		
	end
	
	def parseAuthorsByLine (line, authorString)
	  value = 0
	  developer = 'did not find it'
	  lineNumber = line.to_s
	  authorArray = authorString.split(';')
	  arrayLength = authorArray.size
	  
	  authorArray.each do |x|
	    if (x.include?lineNumber)
	      subString = "#{x}"
	      subStringArray = subString.split('=')
	      number = subStringArray[0]
	      if (number == lineNumber)
	      blame = "#{x}"
	      blameArray = blame.split('=')
	      developer = blameArray[1]
	      end
	    end     
	  end 
	 
	return  developer
	#return developer + ' commited line number ' + lineNumber
	end
	
	def getBlameStats ()
	  exampleBlameArray = []
	  exampleBlameArray[0] = 'Billy'
	  exampleBlameArray[1] = 'Avery'
	  exampleBlameArray[2] = 'Zelda'
	  exampleBlameArray[3] = 'Oscar'
	  exampleBlameArray[4] = 'Billy'
	  
	  x = exampleBlameArray.sort
	  
	  superHash = Hash.new(0)
	  x.each { |name|   superHash[name] += 1 }
	  
	  return superHash
	  
	  
	end
	
	def getDeveloperBlameStats(developerInfo)
	 info = []
	 info = developerInfo.sort 
	 
	 counts = Hash.new(0)
   info.each { |name| counts[name] += 1 }
	 return counts
 end

	
	
end