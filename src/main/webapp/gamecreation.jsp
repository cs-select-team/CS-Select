    <%@ include file="WEB-INF/jspf/header.jspf" %>
        <fmt:bundle basename="locale.Locale">
            <div>
            <%@ include file="WEB-INF/jspf/organiserNavbar.jspf" %>
            <div class="top-buffer"></div>
            <div class="container h-10 title"><fmt:message key="creation"/></div>
            <div class="container" id="gamecreation">
                <div class="row">
                    <div class="col">
                        <div class="col">
                            <div class="row" id="title-area">
                                <input v-model="title" placeholder='<fmt:message key="inputTitle"/>'/>
                            </div> <br>
                            <div class="row" id="invite-area">
                                <invite></invite>
                            </div>
                        </div>
                    </div>
                    <div class="col" id="mode-area">
                        <div class="col">
                            <input type="radio" id="matrix" value="matrix" v-model="mode">
                            <label for="matrix"><fmt:message key="matrixSelect"/></label>
                            <br>
                            <input type="radio" id="binary" value="binary" v-model="mode">
                            <label for="binary"><fmt:message key="binarySelect"/></label>
                        </div>
                        <div class="col" v-if="mode === 'matrix'">
                            <input type="number" id="num" value="10" v-model="numberFeatures" min="0">
                            <label for="num"><fmt:message key="numFeatures"/></label> <br>
                            <input type="number" id="min" value="1" v-model="minSelect" min="0">
                            <label for="min"><fmt:message key="minFeatures"/></label> <br>
                            <input type="number" id="max" value="5" v-model="maxSelect" min="0">
                            <label for="max"><fmt:message key="maxFeatures"/></label>
                        </div>
                    </div>
                    <div class="col" id="pattern-area">
                        <div class="row">
                            <div class="col">
                            <select v-model="selected">
                                <option value='null'>(none)</option>
                                <option v-for="item in savedPatterns" :value="item">{{item.title}}</option>
                                </select>
                            </div>
                            <div v-if="selected !== 'null'" class="col">
                                <pat></pat>
                            </div>
                        </div> <br>
                        <div class="row">
                            <div class="col">
                                <input type="checkbox" id="save" value="save" v-model="savePattern">
                                <label for="save"><fmt:message key="askSavePattern"/></label> <br>
                                <input v-if="savePattern" type="text" id="patternTitle" v-model="pattern"
                                    :placeholder="localisation.patternTitle"/>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="top-buffer"></div>
                <div class="row">
                    <div class="col" id="description-area">
                        <textarea v-model="description" placeholder='<fmt:message key="inputDescription"/>'></textarea>
                    </div>
                    <div class="col" id="termination-area">
                    <div>
                        <input type="radio" id="time" value="time" v-model="terminationtype">
                        <label for="time"><fmt:message key="timeT"/></label>
                        <br>
                        <input type="radio" id="rounds" value="rounds" v-model="terminationtype">
                        <label for="rounds"><fmt:message key="roundsT"/></label>
                    </div>
                    <div v-if="terminationtype === 'time'">
                        <input type="text" id="datetime" v-model="terminationvalue"
                            placeholder='<fmt:message key="timeSyntax"/>'/>
                        <label for="datetime"><fmt:message key="timeInput"/></label>
                    </div>
                    <div v-else-if="terminationtype === 'rounds'">
                        <input type="text" id="numRounds" v-model="terminationvalue"
                        placeholder='<fmt:message key="roundsSyntax"/>'/>
                        <label for="numRounds"><fmt:message key="roundsInput"/></label>
                    </div>
                    </div>
                    <div class="col">
                        <div class="col">
                            <div class="row" id="feature-area">
                                <input v-model="featureSet" placeholder='<fmt:message key="inputFeatures"/>'/>
                            </div> <br>
                            <div class="row" id="database-area">
                                <input v-model="databaseAddress" placeholder='<fmt:message key="inputDatabase"/>'/>
                            </div> <br>
                            <div class="row" id="control-area">
                                <control></control>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            </div>
            <script src="src/js/gamecreation.js"></script>
        </fmt:bundle>
        <%@ include file="WEB-INF/jspf/footer.jspf" %>