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

class BlameController < ScoreController
 
  # this is an administration console
  SECTION=Navigation::SECTION_CONFIGURATION
      
  verify :method => :post, :only => [:create], :redirect_to => {:action => :create}
  def index
     # do not delete
  end
  
  def blameList
   # do not delete
   @projectName = params[:project]
   @metricName = params[:metric]
   @blame = Blame.new(@projectName, @metricName)
  end
  
  #POST/Blame
  def create
    @blame = params[:blame] 
    redirect_to :controller => "blame", :action => "blameList", :project => @blame['projectName'], :metric => @blame['metricName']   
  end   
    
  end 
  