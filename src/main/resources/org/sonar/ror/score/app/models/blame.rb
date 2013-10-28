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

class Blame < ActiveRecord::Base
  attr_accessor :projectName, :currentProjectId, :metricName, :metricId
  
  
   #initialize the variables 
  def initialize(projectName=nil, metric=nil)
    @projectName = projectName
    @metricName = metric
    @currentProjectId = getProjectId()
    @metricId = getMetricId()
            
  end   
     
   
   def getProjectId()          
     return @currentProjectId = Project.find(:all, :conditions => {:name => @projectName})[0].id     
   end
   
   def getMetricId()
      return @metricId = Metric.find(:all, :conditions => {:name => @metricName})[0].id  
     
   end
   
end
   
      
 
   
   
   

    