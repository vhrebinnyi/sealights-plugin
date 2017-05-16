package io.sealights.plugins.sealightsjenkins.buildsteps.cli;

import hudson.DescriptorExtensionList;
import hudson.Extension;
import hudson.Launcher;
import hudson.model.AbstractBuild;
import hudson.model.AbstractProject;
import hudson.model.BuildListener;
import hudson.tasks.BuildStepDescriptor;
import hudson.tasks.Builder;
import io.sealights.plugins.sealightsjenkins.exceptions.SeaLightsIllegalStateException;
import io.sealights.plugins.sealightsjenkins.utils.Logger;
import io.sealights.plugins.sealightsjenkins.utils.PropertiesUtils;
import io.sealights.plugins.sealightsjenkins.utils.StringUtils;
import jenkins.model.Jenkins;
import net.sf.json.JSONObject;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.StaplerRequest;

import java.io.IOException;
import java.util.*;

public class SealightsCLIBuildStep extends Builder {

    public boolean enabled;
    public boolean failBuildIfStepFail;
    public CommandMode commandMode;
    public CLIRunner cliRunner;

    @DataBoundConstructor
    public SealightsCLIBuildStep(boolean enabled, boolean failBuildIfStepFail,
                                 CommandMode commandMode, CLIRunner cliRunner) {
        this.enabled = enabled;
        this.failBuildIfStepFail = failBuildIfStepFail;
        this.commandMode = commandMode;
        this.cliRunner = cliRunner;
    }

    /* * The goal of this method is to support migration of data between versions
    * of this plugin.
    */
    private Object readResolve() {
        if (cliRunner != null) {
            return resolveFromCLiRunner();
        }
        return this;
    }

    private Object resolveFromCLiRunner() {
        StringBuilder  additionalArgs = new StringBuilder();
        if (this.commandMode instanceof CommandMode.ConfigView) {
            if (!StringUtils.isNullOrEmpty(cliRunner.getAppName()))
                ((CommandMode.ConfigView) commandMode).setAppName(cliRunner.getAppName());
            if (!StringUtils.isNullOrEmpty(cliRunner.getBranchName()))
                ((CommandMode.ConfigView) commandMode).setBranchName(cliRunner.getBranchName());
            if (cliRunner.getBuildName() != null)
                ((CommandMode.ConfigView) commandMode).setBuildName(cliRunner.getBuildName());
        } else if (this.commandMode instanceof CommandMode.EndView ||
                this.commandMode instanceof CommandMode.StartView ||
                this.commandMode instanceof CommandMode.ExternalReportView ||
                this.commandMode instanceof CommandMode.UploadReportsView) {
            if (!StringUtils.isNullOrEmpty(cliRunner.getAppName())) {
                additionalArgs.append("appName=" + cliRunner.getAppName() + "\n");
            }
            if (!StringUtils.isNullOrEmpty(cliRunner.getBranchName())) {
                additionalArgs.append("branchName=" + cliRunner.getBranchName() + "\n");
            }

        }
        if (!StringUtils.isNullOrEmpty(cliRunner.getAdditionalArguments()))
            additionalArgs.insert(0, cliRunner.getAdditionalArguments() + "\n");
        (commandMode).setAdditionalArguments(additionalArgs.toString());
        return this;
    }


    public CommandMode getCommandMode() {
        return commandMode;
    }

    public void setCommandMode(CommandMode commandMode) {
        this.commandMode = commandMode;
    }

    public CLIRunner getCliRunner() {
        return cliRunner;
    }

    public void setCliRunner(CLIRunner cliRunner) {
        this.cliRunner = cliRunner;
    }

    public boolean isEnable() {
        return enabled;
    }

    public void setEnable(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isFailBuildIfStepFail() {
        return failBuildIfStepFail;
    }

    public void setFailBuildIfStepFail(boolean failBuildIfStepFail) {
        this.failBuildIfStepFail = failBuildIfStepFail;
    }

    @Override
    public boolean perform(AbstractBuild<?, ?> build, Launcher launcher, BuildListener listener) throws IOException, InterruptedException {
        boolean isStepSuccessful = false;
        Logger logger = new Logger(listener.getLogger(), "SeaLights CLI - " + commandMode.getCurrentMode().getName());

        try {
            if (!enabled) {
                logger.info("Sealights CLI step is disabled.");
                return true;
            }

            CLIHandler cliHandler = new CLIHandler(logger);
            cliRunner = createCLIRunner(commandMode);

            isStepSuccessful = cliRunner.perform(build, launcher, listener, commandMode, cliHandler, logger);
        } catch (Exception e) {
            // for cases when property fields setup is invalid.
            if (e instanceof SeaLightsIllegalStateException) {
                throw e;
            }
            logger.error("Error occurred while performing 'Sealights CLI Build Step'. Error: ", e);
        }

        if (failBuildIfStepFail) {
            return isStepSuccessful;
        }

        return true;
    }

    @Override
    public DescriptorImpl getDescriptor() {
        return (DescriptorImpl) super.getDescriptor();
    }

    @Extension
    public static final class DescriptorImpl extends BuildStepDescriptor<Builder> {

        public DescriptorImpl() {
            load();
        }

        public DescriptorExtensionList<CommandMode, CommandMode.CommandModeDescriptor> getCommandModeDescriptorList() {
            DescriptorExtensionList<CommandMode, CommandMode.CommandModeDescriptor> descriptorList = Jenkins.getInstance().getDescriptorList(CommandMode.class);
            return descriptorList;
        }

        @Override
        public Builder newInstance(StaplerRequest req, JSONObject formData) throws FormException {
            return req.bindJSON(SealightsCLIBuildStep.class, formData);
        }

        public boolean isApplicable(Class<? extends AbstractProject> jobType) {
            return true;
        }

        public String getDisplayName() {
            return "Sealights CLI";
        }
    }

    private CLIRunner createCLIRunner(CommandMode commandMode){
        if(commandMode instanceof CommandMode.ConfigView){
            CommandMode.ConfigView configView=(CommandMode.ConfigView) commandMode;
            return new CLIRunner(configView.getBuildSessionId(),configView.getAppName(),configView.getBranchName(),
                    configView.getBuildName(),configView.getAdditionalArguments(),null);
        }else {
            Properties properties = PropertiesUtils.toProperties(commandMode.getAdditionalArguments());
            String appName = (properties.get("appname")!= null)? properties.get("appname").toString():null;
            String branchName= (properties.get("branchname")!= null)? properties.get("branchname").toString():null;
            String labId= (properties.get("labid")!= null)? properties.get("labid").toString():null;
            CommandBuildName buildName = new CommandBuildName.ManualBuildName(properties.get("buildname").toString());
            return new CLIRunner(commandMode.getBuildSessionId(),appName,branchName,buildName,commandMode.getAdditionalArguments(),labId);
        }
    }
}
