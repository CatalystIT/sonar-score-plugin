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

module ScoreHelper

	def renderLink (*names)
		javascript = "javascript"
	  	link = "link"
	  	script = "script"
	  	part1 = "<script type=\"text/javascript\">\n"
	  	part2 = "\tvar head = document.getElementsByTagName(\"head\")[0];"
	  	part3 = ""
	  	part4 = "</script>"
	  	names.each {|name, i|
		  	type = name.partition("\.")[2].downcase
		  	type = (type.eql? "js") ? javascript : type
		  	isCss = (type.eql? "css")
		  	isJavaScript = (type.eql? javascript)
		  	element = isJavaScript ? script : link
	  		part3 += "\tvar #{element}#{i} = document.createElement(\"#{element}\");\n"
	  		part3 += "\t#{element}#{i}.setAttribute(\"rel\",\"stylesheet\");\n" if isCss
	  		part3 += "\t#{element}#{i}.setAttribute(\"type\",\"text/" + type + "\");\n"
	  		part3 += "\t#{element}#{i}.setAttribute(\"href\",\"/static/score/" + name +"\");\n"
	  		part3 += "\thead.appendChild(#{element}#{i});\n"		
		}
		return part1 + part2 + part3 + part4
	end
	
	def unhidePage
		unhideScript = "<script type='text/javascript'>"
		unhideScript += "window.onload=function()"
		unhideScript += "{document.getElementById('score-page-content')"
		unhideScript += ".setAttribute('class', '');}"
		unhideScript += "</script>"
		return unhideScript
	end
  
	def renderScoreNavBar
		navOptions = Array[["Users", "userlist","user"], ["Teams", "teamlist","team"]]
	  	openDiv = "<div id=\"score-nav\">\n"
	  	divContents = ""
	  	closeDiv = "\n</div>"
	  	lastIndex = navOptions.length - 1
	  	i = 0
	  	navOptions.each { |option|
	  		isCurrent = request.url.include? option[2]
	  		divContents += "<a href=\"/#{option[1]}\"#{isCurrent ? "class=\"current\"><b>" : ">"} "
	  		divContents += "#{option[0]} #{isCurrent ? "</b>" : ""}</a>"
	  		if (i < lastIndex)
	  			divContents += "<span> | </span>"
	  		end
  		}
  		return openDiv + divContents + closeDiv
	end
	
	def renderList(items)
		if (items == nil || items.length < 1)
			return ""
		end
		itemType = items[0].class.name.downcase
		itemType = (itemType.eql? "group") ? "team" : itemType
		openDiv = "<div class = \"#{items[0].class} item-list\">\n"
		divContents = ""
		closeDiv = "</div>"
		items.each { |item|
			if groupIsTeam item
				divContents += "\t<div class = \"#{itemType} item\">\n"
				divContents += "\t\t<a href = \"/#{itemType}profiles/index?#{itemType}id=#{item.id}\">\n"
				divContents += "\t\t\t<img class= \"thumbnail\" src=\"/#{itemType}profiles/display?current_#{itemType}_id=#{item.id}\"  onerror=\"this.src='/images/profiles/#{itemType}s/default.png'\"/>\n"
				divContents += "\t\t\t<span class = \"thumbnail\">#{item.name}</span>\n"
				divContents += "\t\t</a>\n\t</div>\n"
			end
		}
		return openDiv + divContents + closeDiv
	end
	
	def groupIsTeam(group)
		if group.class.name.downcase.eql? "group"
			if group.description.downcase.include?("[team]")
				return true
			else
				return false
			end
		end
		return true
	end
	
	def userIsAdmin(user)
		if user.class.name.downcase.eql? "user"
			if user == nil
				return false
			elsif user.groups.include?(Group.find(:all, :conditions =>{:id => 1})[0])
				return true
			else
				return false
			end
		end
		return false
	end
end